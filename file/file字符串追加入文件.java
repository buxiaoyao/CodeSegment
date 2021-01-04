 // 写入文件
    public void appendFile(String text) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(new File("C:\\Users\\admin\\Desktop\\常用文档\\",
                    "testDataother.json"), true));
            out.write(text + "\n");
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("exception occoured" + e);
        }
    }
