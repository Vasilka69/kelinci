package src;

//import com.fasterxml.jackson.annotation.JsonProperty;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.io.FilenameUtils;
//import org.apache.commons.lang3.tuple.Pair;

//import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//@Slf4j
//@Service
//@RequiredArgsConstructor
public class IndividualPersonImportServiceImpl /*implements IndividualPersonImportService*/ {
    private static final String ERROR_FILE_NOT_SUPPORTED = "ОШИБКА: Приложенный файл не поддерживается";
    private static final String ERROR_USER_IMPORT_CACHE_EMPTY = "ОШИБКА: данные для предварительного просмотра не найдены";
    private static final String ERROR_USER_IMPORT_REPORT_EMPTY = "ОШИБКА: данные для просмотра отчета не найдены";
    private static final String ERROR_SYS_USER_WITH_USERNAME_EXISTS = "Имеется системный пользователь с таким же именем пользователя.";

//    private final List<String> requiredCsvColumnsNames = Arrays.asList(
//            ImportIndividualPersonColumn.FIRST_NAME.getAttributeName(),
//            ImportIndividualPersonColumn.LAST_NAME.getAttributeName(),
//            ImportIndividualPersonColumn.LOGIN.getAttributeName(),
//            ImportIndividualPersonColumn.PASSWORD.getAttributeName(),
//            ImportIndividualPersonColumn.POST.getAttributeName()
//    );

//    private final UserRepository userRepository;
//    private final UserService userService;
//    private final RoleService roleService;
//    private final UserExportExcelService userExportExcelService;
//    private final RoleRepository roleRepository;
//    private final ParseImportCsvService<IndividualPersonImport> parseImportCsvService;


//    private final SelfExpiringHashMap<String, Pair<NavigableMap<String, List<IndividualPersonImport>>, Set<IndividualPersonImport>>> userImportCache =
//            new SelfExpiringHashMap<>(TimeUnit.HOURS.toMillis(1));

//    @Override
//    public List<IndividualPersonImport> parseUsersFromFile(MultipartFile file) {
//        final var result = parseUsers(file);
//
//        NavigableMap<String, List<IndividualPersonImport>> groupedByUsername = new TreeMap<>();
//        final var iWrapper = new int[]{result.size()};
//        result.forEach(userDtoFromImport -> {
//            try {
//                var userFromImport = IndividualPerson.builder()
//                        .id(UUID.randomUUID().toString())
//                        .username(userDtoFromImport.getUsername())
//                        .password(userDtoFromImport.getPassword())
//                        .accountNonLocked(true)
//                        .firstName(userDtoFromImport.getFirstName())
//                        .lastName(userDtoFromImport.getLastName())
//                        .patronymic(userDtoFromImport.getPatronymic())
//                        .changePassword(false)
//                        .post(userDtoFromImport.getPost())
//                        .email(userDtoFromImport.getEmail())
//                        .build();
//
//                userDtoFromImport.setIndividualPerson(userFromImport);
//
//                checkAndProcessRoleIds(userDtoFromImport);
//
//                var groupingList = groupedByUsername.get(
//                        StringUtils.hasText(userDtoFromImport.getUsername()) ? userDtoFromImport.getUsername() : ""
//                );
//
//                // попытка найти пользователя
//                var existedIp = attemptFindByUsername(userDtoFromImport, userFromImport, groupingList, groupedByUsername);
//                if (existedIp == null) {
//                    return;
//                }
//
//                // попытка обновить пользователя
//                attemptUpdate(userDtoFromImport, userFromImport, existedIp, groupingList, groupedByUsername, iWrapper);
//            } catch (RuntimeException ex) {
//                userDtoFromImport.setErrorDescription(ex.getMessage());
//            }
//        });
//
//        var currentUserToken = SecurityUtils.getCurrentUserToken();
//        if (currentUserToken != null) {
//            Pair<NavigableMap<String, List<IndividualPersonImport>>, Set<IndividualPersonImport>> importResult = Pair.of(groupedByUsername, new HashSet<>());
//            userImportCache.put(currentUserToken, importResult);
//        }
//
//        return result;
//    }
//
//    private void checkAndProcessRoleIds(IndividualPersonImport ipDtoFromImport) {
//        List<String> roles = null;
//        if (!CollectionUtils.isEmpty(ipDtoFromImport.getRolesIds())) {
//            roles = ipDtoFromImport.getRolesIds().stream().filter(StringUtils::hasText).collect(Collectors.toList());
//        }
//        var newIpFromImport = ipDtoFromImport.getIndividualPerson();
//        if (newIpFromImport == null) {
//            throw new IllegalArgumentException("ОШИБКА: пользователь должен был проиницализирован перед вызовом метода checkAndProcessRoleIds");
//        }
//        try {
//            List<Role> roleList = CollectionUtils.isEmpty(roles) ? new ArrayList<>() : roleService.findByKeys(roles);
//            if (!CollectionUtils.isEmpty(roleList)) {
//                ipDtoFromImport.setRolesNames(roleList.stream().map(Role::getName).collect(Collectors.joining(", ")));
//            }
//            newIpFromImport.setRoles(roleList.stream().map(Role::getId).collect(Collectors.toSet()));
//        } catch (ResourceNotFoundException ex) {
//            newIpFromImport.setRoles(null);
//            ipDtoFromImport.setErrorDescription(ex.getMessage());
//        }
//    }
//
//    @Override
//    public UserImportResultInfo<IndividualPersonImport> importUsers() {
//        UserImportResultInfo<IndividualPersonImport> resultInfo = new UserImportResultInfo<>();
//        var currentUserToken = SecurityUtils.getCurrentUserToken();
//        if (currentUserToken == null || !userImportCache.containsKey(currentUserToken)) {
//            throw new ResourceNotFoundException(ERROR_USER_IMPORT_CACHE_EMPTY);
//        }
//
//        var importResult = userImportCache.get(currentUserToken);
//        List<IndividualPersonImport> users = new ArrayList<>();
//        importResult.getLeft().forEach((s, individualPersonImports) ->
//                individualPersonImports.stream().filter(el -> !ImportStatus.EXISTED.equals(el.getImportStatus()))
//                        .forEach(users::add)
//        );
//        var withErrors = importResult.getRight();
//
//        // пользователи, которые были импортированы нам больше не нужны, зануляем для GC
//        userImportCache.put(currentUserToken, Pair.of(null, withErrors));
//
//        users.forEach(user -> {
//            if (user.getErrorDescription() != null) {
//                withErrors.add(user);
//            } else {
//                try {
//                    userRepository.save(user.getIndividualPerson());
//                } catch (RuntimeException e) {
//                    user.setErrorDescription(e.getMessage());
//                    withErrors.add(user);
//                }
//            }
//        });
//        var addedCount = users.stream()
//                .filter(ip -> ip.getImportStatus().equals(ImportStatus.NEW) &&
//                        !withErrors.contains(ip)).count();
//        var updatedCount = users.stream()
//                .filter(ip -> ip.getImportStatus().equals(ImportStatus.UPDATE) &&
//                        !withErrors.contains(ip)).count();
//        List<String> messages = new ArrayList<>();
//        messages.add(String.format("Создано записей (%d).", addedCount));
//        messages.add(String.format("Изменено записей (%d).", updatedCount));
//        if (!withErrors.isEmpty()) {
//            messages.add("Импорт завершен с ошибками!");
//        }
//        resultInfo.setMessage(String.join("\n\r", messages));
//        resultInfo.setResult(withErrors);
//        return resultInfo;
//    }
//
//    @Override
//    public ByteArrayInputStream generateUserImportTemplate() throws IOException {
//        return userExportExcelService.generateIndividualPersonImportTemplate();
//    }
//
//    @Override
//    public void deleteImportUsers(List<ImportDeleteByUsernameAndIndexInfo> deletedByUsernameAndIndex) {
//        var currentUserToken = SecurityUtils.getCurrentUserToken();
//        if (currentUserToken == null || !userImportCache.containsKey(currentUserToken)) {
//            throw new ResourceNotFoundException(ERROR_USER_IMPORT_CACHE_EMPTY);
//        }
//
//        var importResult = userImportCache.get(currentUserToken).getLeft();
//        Set<String> groupWhichHaveBeenDeleted = new HashSet<>();
//        deletedByUsernameAndIndex.forEach(deleteInfo -> {
//            validate(deleteInfo);
//            var individualPersonImports = importResult.get(deleteInfo.getUsername());
//            if (individualPersonImports != null) {
//                IndividualPersonImport individualPersonImport = getByDeleteInfo(individualPersonImports, deleteInfo);
//                deleteImportUser(individualPersonImport, deleteInfo, importResult, groupWhichHaveBeenDeleted, individualPersonImports);
//            } else if (!groupWhichHaveBeenDeleted.contains(deleteInfo.getUsername())) {
//                throw new ResourceNotFoundException(
//                        String.format("ОШИБКА: данные для предварительного просмотра по имени пользователя %s не найдены",
//                                deleteInfo.getUsername()));
//            }
//        });
//    }
//
//    private void validate(ImportDeleteByUsernameAndIndexInfo deleteInfo) {
//        if (deleteInfo.getIndex() == null || deleteInfo.getUsername() == null) {
//            throw new ResourceIllegalArgumentException("ОШИБКА: индекс или имя пользователя null.");
//        }
//    }
//
//    private IndividualPersonImport getByDeleteInfo(List<IndividualPersonImport> individualPersonImports, ImportDeleteByUsernameAndIndexInfo deleteInfo) {
//        var foundOpt = individualPersonImports.stream()
//                .filter(el ->
//                        el.getUsername().equals(deleteInfo.getUsername()) && el.getIndex().equals(deleteInfo.getIndex()))
//                .findFirst();
//        if (foundOpt.isEmpty()) {
//            throw new ResourceNotFoundException(
//                    String.format("ОШИБКА: данные для предварительного просмотра по имени пользователя %s и индексу %s не найдены",
//                            deleteInfo.getUsername(), deleteInfo.getIndex()));
//        }
//
//        return foundOpt.get();
//    }
//
//    private void deleteImportUser(
//            IndividualPersonImport individualPersonImport,
//            ImportDeleteByUsernameAndIndexInfo deleteInfo,
//            Map<String, List<IndividualPersonImport>> importResult,
//            Set<String> groupWhichHaveBeenDeleted,
//            List<IndividualPersonImport> individualPersonImports
//    ) {
//        var foundStatus = individualPersonImport.getImportStatus();
//        if (foundStatus.equals(ImportStatus.EXISTED)) {
//            importResult.remove(deleteInfo.getUsername());
//            groupWhichHaveBeenDeleted.add(deleteInfo.getUsername());
//            return;
//        }
//        individualPersonImports.removeIf(el -> el.getIndex().equals(deleteInfo.getIndex()));
//        if (individualPersonImports.isEmpty()
//                || (individualPersonImports.size() == 1
//                && individualPersonImports.stream()
//                .allMatch(el -> el.getImportStatus().equals(ImportStatus.EXISTED)))) {
//            importResult.remove(deleteInfo.getUsername());
//            groupWhichHaveBeenDeleted.add(deleteInfo.getUsername());
//            return;
//        }
//        if (foundStatus.equals(ImportStatus.NEW)) {
//            individualPersonImports.stream().filter(el -> el.getIndex() > individualPersonImport.getIndex())
//                    .forEach(el -> {
//                        if (ERROR_SYS_USER_WITH_USERNAME_EXISTS.equals(el.getErrorDescription())) {
//                            return;
//                        }
//                        attemptBeforeAdd(el, el.getIndividualPerson());
//                    });
//        }
//    }
//
//    @Override
//    public NavigableMap<String, List<IndividualPersonImport>> getImportedUsers() {
//        var currentUserToken = SecurityUtils.getCurrentUserToken();
//        if (currentUserToken != null && userImportCache.containsKey(currentUserToken)) {
//            return userImportCache.get(currentUserToken).getLeft();
//        } else {
//            throw new ResourceNotFoundException(ERROR_USER_IMPORT_CACHE_EMPTY);
//        }
//    }
//
//    @Override
//    public Set<IndividualPersonImport> getImportUsersReport() {
//        var currentUserToken = SecurityUtils.getCurrentUserToken();
//        if (currentUserToken != null && userImportCache.containsKey(currentUserToken)) {
//            return userImportCache.get(currentUserToken).getRight();
//        } else {
//            throw new ResourceNotFoundException(ERROR_USER_IMPORT_REPORT_EMPTY);
//        }
//    }
//
//    @Override
//    public void generateUserImportReport(OutputStream outputStream) throws IOException {
//        var currentUserToken = SecurityUtils.getCurrentUserToken();
//        Set<IndividualPersonImport> ipWithErrors;
//
//        if (currentUserToken != null && userImportCache.containsKey(currentUserToken)) {
//            ipWithErrors = userImportCache.get(currentUserToken).getRight();
//        } else {
//            throw new ResourceNotFoundException(ERROR_USER_IMPORT_REPORT_EMPTY);
//        }
//
//        var byteArrayInputStream = generateUserImportTemplate();
//        try (XSSFWorkbook workbook = new XSSFWorkbook(byteArrayInputStream)) {
//            var evaluator = workbook.getCreationHelper().createFormulaEvaluator();
//            var userSheet = workbook.getSheetAt(1);
//            var createUserSheet = workbook.getSheetAt(2);
//            var iWrapper = new int[]{1};
//            var headers = userSheet.getRow(0);
//            var headerCellStyle = headers.getCell(0).getCellStyle();
//            var errorDescriptionHeaderCell = headers.createCell(9);
//            errorDescriptionHeaderCell.setCellValue("Описание ошибки");
//            errorDescriptionHeaderCell.setCellStyle(headerCellStyle);
//
//            XSSFCellStyle[] rowCellStyleWrapper = new XSSFCellStyle[1];
//            ipWithErrors.forEach(ip -> {
//                var row = userSheet.getRow(iWrapper[0]);
//                var createUserRow = createUserSheet.getRow(iWrapper[0]);
//                iWrapper[0]++;
//                if (row == null || createUserRow == null) {
//                    return;
//                }
//                // USERNAME
//                setCellValue(row, createUserRow, evaluator, 0, ip.getUsername());
//                if (rowCellStyleWrapper[0] == null) {
//                    rowCellStyleWrapper[0] = row.getCell(0).getCellStyle();
//                }
//                // FIO
//                setCellValue(row, createUserRow, evaluator, 1, ip.getLastName());
//                setCellValue(row, createUserRow, evaluator, 2, ip.getFirstName());
//                setCellValue(row, createUserRow, evaluator, 3, ip.getPatronymic());
//                // POST
//                setCellValue(row, createUserRow, evaluator, 4, ip.getPost());
//                // EMAIL
//                setCellValue(row, createUserRow, evaluator, 5, ip.getEmail());
//                // PASS
//                setCellValue(row, createUserRow, evaluator, 6, ip.getPassword());
//                // ROLES
//                if (!CollectionUtils.isEmpty(ip.getRolesIds())) {
//                    var roleIds = ip.getRolesIds().stream()
//                            .filter(roleRepository::existsById).collect(Collectors.toList());
//                    if (!CollectionUtils.isEmpty(roleIds)) {
//                        var rolesNames = roleIds.stream()
//                                .map(roleId -> roleRepository.findById(roleId).map(Role::getName).orElseThrow(() -> new ResourceNotFoundException("роль", roleId)))
//                                .collect(Collectors.toList());
//                        setCellValue(row, null, null, 7, String.join(";", rolesNames));
//                        setCellValue(createUserRow, null, null, 7, String.join(";", roleIds));
//                    }
//                }
//                // ERROR DESCRIPTION
//                if (Objects.isNull(row.getCell(9))) {
//                    row.createCell(9);
//                }
//                setCellValue(row, null, null, 9, ip.getErrorDescription());
//                row.getCell(9).setCellStyle(rowCellStyleWrapper[0]);
//            });
//            workbook.write(outputStream);
//        }
//    }
//
//    @Override
//    public void deleteImportUsersReport() {
//        var currentUserToken = SecurityUtils.getCurrentUserToken();
//        if (currentUserToken != null) {
//            userImportCache.remove(currentUserToken);
//        }
//    }
//
//    private List<IndividualPersonImport> parseUsers(MultipartFile file) {
//        var extension = FilenameUtils.getExtension(file.getOriginalFilename());
//        if (!StringUtils.hasText(extension)) {
//            throw new ResourceIllegalArgumentException(ERROR_FILE_NOT_SUPPORTED);
//        }
//
//        try {
//            if (ParseImportCsvService.CSV_EXTENSION.equalsIgnoreCase(extension)) {
//                return parseUsersFromCsv(file);
//            } else if (ParseImportCsvService.EXCEL_EXTENSIONS.contains(extension.toLowerCase())) {
//                return parseUsersFromExcel(file);
//            } else {
//                throw new ResourceIllegalArgumentException(ERROR_FILE_NOT_SUPPORTED);
//            }
//        } catch (IOException e) {
//            log.error(e.getMessage(), e);
//            throw new IllegalArgumentException("ОШИБКА: импорт пользователей завершился не корректно");
//        }
//    }
//
//    private List<IndividualPersonImport> parseUsersFromCsv(MultipartFile file) throws IOException {
//        validateFile(file);
//        final int[] iWrapper = {0};
//        return parseImportCsvService.parseUsersFromCsv(
//                file,
//                requiredCsvColumnsNames,
//                IndividualPersonImport.class,
//                IndividualPersonImportCsvMixin.class,
//                (IndividualPersonImport temp) -> {
//                    temp.setIndex(iWrapper[0]++);
//                    if (StringUtils.hasText(temp.getRolesIdsNotFormatted())) {
//                        if (temp.getRolesIdsNotFormatted().startsWith("[") && temp.getRolesIdsNotFormatted().endsWith("]")) {
//                            var roles = temp.getRolesIdsNotFormatted().substring(1, temp.getRolesIdsNotFormatted().length() - 1);
//                            if (StringUtils.hasText(roles)) {
//                                temp.setRolesIds(Arrays.stream(roles.split(","))
//                                        .filter(StringUtils::hasText)
//                                        .map(String::trim).collect(Collectors.toList()));
//                            }
//                        } else {
//                            List<String> rolesIds = new ArrayList<>(1);
//                            rolesIds.add(temp.getRolesIdsNotFormatted().trim());
//                            temp.setRolesIds(rolesIds);
//                        }
//                    }
//                }
//        );
//    }

    public static boolean hasText(String str) {
        return (str != null && !str.isEmpty() && containsText(str));
    }

    private static boolean containsText(CharSequence str) {
        int strLen = str.length();
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public static boolean isEmpty(Collection<?> collection) {
        return (collection == null || collection.isEmpty());
    }

    public List<IndividualPersonImport> parseUsersFromExcel(File file) throws IOException, InvalidFormatException {
//        validateFile(file);
        List<IndividualPersonImport> result = new ArrayList<>();
        try (XSSFWorkbook workbook = new XSSFWorkbook(file)) {
//            org.apache.poi.xssf.usermodel.XSSFSheet createUserSheet = workbook.getSheetAt(2);
            org.apache.poi.xssf.usermodel.XSSFSheet createUserSheet = workbook.getSheetAt(0);

            int lastRowNum = createUserSheet.getLastRowNum();
            for (int i = 1; i <= lastRowNum; i++) {
                IndividualPersonImport temp = new IndividualPersonImport();
                try {
                    XSSFRow row = createUserSheet.getRow(i);
                    if (row == null || !hasText(getCellValue(row, 0, false))) {
                        continue;
                    }

                    // USERNAME
                    temp.setUsername(getCellValue(row, 0, false));
                    // FIO
                    temp.setLastName(getCellValue(row, 1, false));
                    temp.setFirstName(getCellValue(row, 2, false));
                    temp.setPatronymic(getCellValue(row, 3, false));
                    // POST
                    temp.setPost(getCellValue(row, 4, false));
                    // EMAIL
                    temp.setEmail(getCellValue(row, 5, false));
                    // PASS
                    temp.setPassword(getCellValue(row, 6, true));
                    // ROLES
                    List<String> roleIds = getCellValues(row, 7);
                    if (!isEmpty(roleIds)) {
                        temp.setRolesIds(roleIds);
                    }
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    temp.setErrorDescription(ex.getMessage());
                }
                temp.setIndex(i - 1);
                result.add(temp);
            }
        }
        return result;
    }

    private String getCellValue(XSSFRow row, int index, boolean allowEmpty) {
        org.apache.poi.xssf.usermodel.XSSFCell cell = row.getCell(index);
        if (cell == null) {
            return null;
        }

        String value = cell.getStringCellValue();
        if (hasText(value) || allowEmpty) {
            return value;
        }
        return null;
    }

    private List<String> getCellValues(XSSFRow row, int index) {
        org.apache.poi.xssf.usermodel.XSSFCell cell = row.getCell(index);
        if (cell == null) {
            return Collections.emptyList();
        }

        String values = cell.getStringCellValue();
        if (hasText(values)) {
            return Stream.of(values.split(";"))
                    .filter(IndividualPersonImportServiceImpl::hasText)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

//    private void setCellValue(XSSFRow row, XSSFRow createUserRow, FormulaEvaluator evaluator, int index, String value) {
//        var cell = row.getCell(index);
//        if (cell == null || !StringUtils.hasText(value)) {
//            return;
//        }
//        cell.setCellValue(value);
//
//        if (createUserRow != null) {
//            var createUserCell = createUserRow.getCell(index);
//            if (createUserCell != null && evaluator != null) {
//                evaluator.evaluateFormulaCell(createUserCell);
//            }
//        }
//    }

//    private void validateFile(MultipartFile file) {
//        if (file == null) {
//            throw new ResourceIsNullException("ОШИБКА: не передан файл для импорта пользователей");
//        }
//
//        if (file.isEmpty()) {
//            throw new ResourceIsNullException("ОШИБКА: передан пустой файл для импорта пользователей");
//        }
//
//        if (!StringUtils.hasText(file.getOriginalFilename())) {
//            throw new ResourceIllegalArgumentException("ОШИБКА: невозможно определить наименование файла и его расширение");
//        }
//    }

//    private IndividualPerson attemptFindByUsername(
//            IndividualPersonImport userDtoFromImport,
//            IndividualPerson userFromImport,
//            List<IndividualPersonImport> groupingList,
//            NavigableMap<String, List<IndividualPersonImport>> groupedByUsername
//    ) {
//        try {
//            if (!StringUtils.hasText(userDtoFromImport.getUsername())) {
//                userDtoFromImport.setErrorDescription("Имя пользователя не можем быть пустым.");
//                throw new ResourceIllegalArgumentException("");
//            }
//
//            User user = userService.findByUsername(userDtoFromImport.getUsername());
//            if (user instanceof IndividualPerson) {
//                return (IndividualPerson) user;
//            }
//
//            userDtoFromImport.setErrorDescription(ERROR_SYS_USER_WITH_USERNAME_EXISTS);
//            userDtoFromImport.setImportStatus(ImportStatus.NEW);
//            userDtoFromImport.setIndividualPerson(userFromImport);
//            if (groupingList != null) {
//                groupingList.add(userDtoFromImport);
//            } else {
//                ArrayList<IndividualPersonImport> listOfNew = new ArrayList<>();
//                listOfNew.add(userDtoFromImport);
//                groupedByUsername.put(userDtoFromImport.getUsername(), listOfNew);
//            }
//            return null;
//        } catch (ResourceIllegalArgumentException | ResourceNotFoundException e) {
//            log.warn(e.getMessage(), e);
//            userDtoFromImport.setImportStatus(ImportStatus.NEW);
//            userDtoFromImport.setIndividualPerson(userFromImport);
//            if (groupingList != null) {
//                groupingList.add(userDtoFromImport);
//                if (StringUtils.hasText(userDtoFromImport.getUsername())) {
//                    userDtoFromImport.setErrorDescription(
//                            String.format("В импорте уже создаётся новый пользователь с таким именем: %s", userDtoFromImport.getUsername()));
//                }
//                return null;
//            } else {
//                ArrayList<IndividualPersonImport> listOfNew = new ArrayList<>();
//                listOfNew.add(userDtoFromImport);
//                groupedByUsername.put(userDtoFromImport.getUsername(), listOfNew);
//                if (userDtoFromImport.getErrorDescription() == null) {
//                    attemptBeforeAdd(userDtoFromImport, userFromImport);
//                }
//                return null;
//            }
//        }
//    }
//
//    private void attemptUpdate(
//            IndividualPersonImport userDtoFromImport,
//            IndividualPerson userFromImport,
//            IndividualPerson existedIp,
//            List<IndividualPersonImport> groupingList,
//            NavigableMap<String, List<IndividualPersonImport>> groupedByUsername,
//            int[] iWrapper
//    ) {
//        userFromImport.setId(existedIp.getId());
//
//        if (groupingList == null) {
//            groupingList = new ArrayList<>();
//            groupedByUsername.put(userDtoFromImport.getUsername(), groupingList);
//
//            if (!SecurityUtils.isSysAdmin()) {
//                userDtoFromImport.setErrorDescription("Недостаточно прав доступа для внесения изменений");
//            }
//
//            List<Role> existedRoleList = CollectionUtils.isEmpty(existedIp.getRoles()) ? new ArrayList<>() : roleService.findByKeys(existedIp.getRoles());
//
//            IndividualPersonImport existedIpDto = new IndividualPersonImport(
//                    existedIp.getFirstName(),
//                    existedIp.getLastName(),
//                    existedIp.getPatronymic(),
//                    existedIp.getPost(),
//                    existedIp.getEmail(),
//                    "",
//                    existedIp.getUsername(),
//                    existedRoleList.stream().map(Role::getName).collect(Collectors.joining(", ")),
//                    existedRoleList.stream().map(Role::getId).collect(Collectors.toList()),
//                    existedIp,
//                    ImportStatus.EXISTED,
//                    iWrapper[0]++,
//                    null,
//                    null
//            );
//
//            groupingList.add(existedIpDto);
//        }
//
//        userDtoFromImport.setImportStatus(ImportStatus.UPDATE);
//        userDtoFromImport.setIndividualPerson(userFromImport);
//        groupingList.add(userDtoFromImport);
//        if (!StringUtils.hasText(userFromImport.getPassword())) {
//            userFromImport.setPassword(null);
//        }
//        if (userDtoFromImport.getErrorDescription() == null) {
//            attemptBeforeUpdate(userDtoFromImport, userFromImport);
//        }
//    }
//
//
//    private void attemptBeforeAdd(IndividualPersonImport userDtoFromImport, IndividualPerson userFromImport) {
//        try {
//            userService.beforeAdd(userFromImport);
//            userDtoFromImport.setErrorDescription(null);
//        } catch (Exception e) {
//            userDtoFromImport.setErrorDescription(e.getMessage());
//        }
//    }
//
//    private void attemptBeforeUpdate(IndividualPersonImport userDtoFromImport, IndividualPerson userFromImport) {
//        try {
//            userService.beforeUpdate(userFromImport);
//            userDtoFromImport.setErrorDescription(null);
//        } catch (Exception e) {
//            userDtoFromImport.setErrorDescription(e.getMessage());
//        }
//    }
}

//interface IndividualPersonImportCsvMixin {
//    @JsonProperty(ImportIndividualPersonColumn.ROLES_ATTRIBUTE_NAME)
//    void setRolesIdsNotFormatted(String value);
//
//    @JsonProperty(ImportIndividualPersonColumn.ROLES_ATTRIBUTE_NAME)
//    String getRolesIdsNotFormatted();
//}
