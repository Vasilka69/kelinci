import java.util.Arrays;

public class CSV
{
    public static void main(final String[] args) {
//        if (args.length != 1) {
//            System.err.println("Expects file name as parameter");
//            return;
//        }
//
//        try {
//            File imageFile = new File(args[0]);
//            BufferedImage image = ImageIO.read(imageFile);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
        System.out.println(Arrays.toString(args));
        System.out.println("=========");
        System.out.println(String.join("; ", args));
        System.out.println("Done.");
    }
}
