    @Test
    public void move() throws IOException {
        String strIn="C:\\xxx1";
        String strOut="C:\\xxx2";
        List<String> fileName = getFileName();

        for (String name : fileName) {
            try {
                File fileIn = new File(strIn + File.separator + name + ".png");
                File fileOut = new File(strOut + File.separator + name + ".png");
                copyFileUsingFileStreams(fileIn, fileOut);
            }catch (Exception e){
                System.out.println(e.getMessage()+e.getCause());
            }

        }
        System.out.println(fileName.size());
        System.out.println("move ok");
    }

    private static void copyFileUsingFileStreams(File source, File dest)
            throws IOException {
        InputStream input = null;
        OutputStream output = null;
        try {
            input = new FileInputStream(source);
            output = new FileOutputStream(dest);
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buf)) > 0) {
                output.write(buf, 0, bytesRead);
            }
        } finally {
            if (output != null) {
                output.close();
            }
            if (input != null) {
                input.close();
            }
        }
    }

    private static List<String> getFileName(){
        String[] ids = new String[]{
                "aaaaaaaaaaaaaa",

        };
        List<String> aa=new ArrayList<>();
        for (String id : ids) {
            aa.add(id);
        }
        return aa;
    }
