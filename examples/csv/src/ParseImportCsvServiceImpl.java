import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.kamatech.szi.common.exception.ResourceIllegalArgumentException;
import ru.kamatech.szi.common.exception.ResourceIsNullException;
import ru.kamatech.szi.security.dto.IndividualPersonImport;
import ru.kamatech.szi.security.service.ParseImportCsvService;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class ParseImportCsvServiceImpl implements ParseImportCsvService {

    private final CsvMapper csvMapper = new CsvMapper();

    @Override
    public List<IndividualPersonImport> parseUsersFromCsv(MultipartFile file,
                                                          List<String> requiredColumnsNames) throws IOException {
        validateFile(file);
        final var csvSeparator = ';';
        final var charset = detectCharset(file, requiredColumnsNames);

        var schema = csvMapper
                .configure(CsvParser.Feature.FAIL_ON_MISSING_HEADER_COLUMNS, false)
                .schemaFor(IndividualPersonImport.class)
                .withHeader()
                .withColumnSeparator(csvSeparator)
                .withColumnReordering(true);

        var result = new ArrayList<IndividualPersonImport>();

        var inputStream = new BufferedInputStream(file.getInputStream());
        deleteUtf8Bom(inputStream, charset);

        MappingIterator<IndividualPersonImport> mappingIterator = csvMapper.readerFor(IndividualPersonImport.class)
                .with(schema)
                .readValues(new InputStreamReader(inputStream, charset));

        var csvSchema = (CsvSchema) mappingIterator.getParser().getSchema();
        var iterator = csvSchema.iterator();

        var columnsNamesInSchemaList = new ArrayList<>();
        while (iterator.hasNext()) {
            columnsNamesInSchemaList.add(iterator.next().getName());
        }
        if (!columnsNamesInSchemaList.containsAll(requiredColumnsNames)) {
            var emptyColumns = requiredColumnsNames.stream()
                    .filter(col -> !columnsNamesInSchemaList.contains(col))
                    .collect(Collectors.joining(", "));

            throw new ResourceIllegalArgumentException(String.format("В csv отсутствуют обязательные столбцы: %s.", emptyColumns));
        }

        var index = new AtomicInteger(0);
        while (mappingIterator.hasNextValue()) {
            var individualPersonImport = mappingIterator.nextValue();
            individualPersonImport.setIndex(index.getAndIncrement());
            result.add(individualPersonImport);
        }
        return result;
    }

    private void validateFile(MultipartFile file) {
        if (file == null) {
            throw new ResourceIsNullException("ОШИБКА: для импорта пользователей из csv не передан файл");
        }

        if (file.isEmpty()) {
            throw new ResourceIsNullException("ОШИБКА: для импорта пользователей из csv передан пустой файл");
        }

        var filename = file.getOriginalFilename();
        if (!StringUtils.hasText(filename)) {
            throw new ResourceIllegalArgumentException("ОШИБКА: невозможно определить наименование файла и его расширение " +
                    "(для импорта пользователей требуется csv файл)");
        }

        var extension = FilenameUtils.getExtension(filename);
        if (!StringUtils.hasText(extension) || !"csv".equals(extension)) {
            throw new ResourceIllegalArgumentException("ОШИБКА: для импорта пользователей требуется csv файл");
        }
    }

    private Charset detectCharset(MultipartFile file, List<String> requiredColumnsNames) {
        final var defaultCharset = Charset.forName("Windows-1251");
        final var supportedCharsets = Arrays.asList(defaultCharset, StandardCharsets.UTF_8);
        for (Charset charset : supportedCharsets) {
            try {
                var content = new String(file.getBytes(), charset);
                var hasColumn = requiredColumnsNames.stream()
                        .anyMatch(content::contains);
                if (hasColumn) {
                    return charset;
                }
            } catch (IOException e) {
                // ignore
            }
        }

        return defaultCharset;
    }

    private void deleteUtf8Bom(InputStream inputStream, Charset charset) {
        if (charset != StandardCharsets.UTF_8) {
            return;
        }
        try {
            var available = inputStream.available();
            if (available > 2) {
                inputStream.mark(24);
                var wrapperBOM = new char[]{'\uFEFF'};
                var bytesBOM = new String(wrapperBOM).getBytes(charset);

                var buffer = new byte[3];
                inputStream.read(buffer, 0, 3);
                for (var i = 0; i < buffer.length; i++) {
                    if (buffer[i] != bytesBOM[i]) {
                        inputStream.reset();
                        break;
                    }
                }
            }
        } catch (IOException e) {
            // ignore
        }
    }
}
