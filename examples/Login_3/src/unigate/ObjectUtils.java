package src.unigate;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ObjectUtils {

    public static <T> T fromJson(byte[] bytes, Class<T> clazz) {
        try {
            return ObjectMapperUtils.getObjectMapper().readValue(bytes, clazz);
        } catch (Exception ex) {
            return null;
        }
    }

//    public static <T> T fromJson(String string, Class<T> clazz) {
//        try {
//            return ObjectMapperUtils.getObjectMapper().readValue(string, clazz);
//        } catch (Exception ex) {
//            return null;
//        }
//    }
//
//    public static <T> T fromJson(String string, TypeReference<T> valueTypeRef) {
//        try {
//            return ObjectMapperUtils.getObjectMapper().readValue(string, valueTypeRef);
//        } catch (Exception ex) {
//            return null;
//        }
//    }
//
//    public static <T> T fromFile(String fileName, Class<T> clazz) {
//        return fromJson(readFile(fileName), clazz);
//    }
//
//    public static <T> T fromFile(String fileName, TypeReference<T> valueTypeRef) {
//        return fromJson(readFile(fileName), valueTypeRef);
//    }
//
//    public static String toJson(Object object) {
//        try {
//            return ObjectMapperUtils.getObjectMapper().writeValueAsString(object);
//        } catch (Exception ex) {
//            return null;
//        }
//    }
//
//    public static String readFile(String fileName) {
//        try {
//            return Files.readString(getFilePath(fileName));
//        } catch (Exception ex) {
//            return null;
//        }
//    }
//
//    public static Path getFilePath(String fileName) {
//        File file = null;
//
//        try {
//            var classLoader = ObjectUtils.class.getClassLoader();
//            var resource = classLoader.getResource(fileName);
//            if (resource != null) {
//                if (Objects.equals(resource.toURI().getScheme(), "jar")) {
//                    synchronized (ObjectUtils.class) {
//                        file = new File(resource.toString().substring(resource.toString().lastIndexOf("/") + 1));
//                        if (file.exists()) {
//                            return file.toPath();
//                        }
//                        IOUtils.copy(Objects.requireNonNull(classLoader.getResourceAsStream(fileName)), new FileOutputStream(file));
//                    }
//                } else {
//                    file = Paths.get(resource.toURI()).toFile();
//                }
//            }
//        } catch (IOException | URISyntaxException e) {
//            throw new ResourceIllegalArgumentException("File not found");
//        }
//        if (file == null || !file.exists()) {
//            throw new ResourceIllegalArgumentException("File not found");
//        }
//
//        return file.toPath();
//    }
}
