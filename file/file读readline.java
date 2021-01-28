  String filePath="xxx.csv";
  try( BufferedReader br = new BufferedReader(new FileReader(filePath))) {
      String contentLine;
      while ((contentLine = br.readLine()) != null) {
          //读取每一行，并输出
          //System.out.println(contentLine);//将每一行追加到arr1
          if(!contentLine.contains("timestamp")){
              if(contentLine.contains(",")){
              }
          }
      }
  }catch (Exception e){
      e.printStackTrace();
  }
