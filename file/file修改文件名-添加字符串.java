import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileTest {

    static String FILEPATH = "C:\\Users\\admin\\Desktop\\hello_world";
    static String SUBSTRING = "helloworld_helloworld_helloworld_helloworld_";

    public static void main(String[] args) throws IOException {
        // 需要替换的字符串
        String subString = SUBSTRING;
        // 获取一一对应的文件名称
        List<String> oldFileNames = getFileNames();
        List<String> newFileNames = new ArrayList<String>();
        for (int i = 0; i < oldFileNames.size(); i++) {
            String name = oldFileNames.get(i);
            if (name.contains(subString)) {
                String newString = name.replace(subString, "");
                newFileNames.add(newString);
            } else {
                newFileNames.add(name);
            }
        }

        for (int i = 0; i < oldFileNames.size(); i++) {
            changeName(oldFileNames.get(i), newFileNames.get(i));

        }

    }

    /*
     * 获取所有的文件夹下的所有的文件（非文件夹）
     */
    private static List<String> getFileNames() {
        List<String> listName = new ArrayList<String>();
        String filepath = FILEPATH;
        File file = new File(filepath);

        if (file.isDirectory()) {
            String[] filelist = file.list();
            System.out.println("文件个数：" + filelist.length);
            for (int i = 0; i < filelist.length; i++) {
                String name = filepath + "\\" + filelist[i];
                listName.add(name);
            }

        }
        return listName;
    }

    /*
     * 修改文件的名称
     */
    private static void changeName(String oldName, String newName)
            throws IOException {
        String filename = oldName;
        File oldFile = new File(filename);
        if (!oldFile.exists()) {
            return;
        }
        System.out.println("修改前文件名称是：" + oldFile.getName());
        File newFile = new File(newName);
        System.out.println("修改后文件名称是：" + newFile.getName());
        if (oldFile.renameTo(newFile)) {
            System.out.println("修改成功!");
        } else {
            System.out.println("修改失败");
        }
    }
}
