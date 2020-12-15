  
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.junit.Test;
    // https://mvnrepository.com/artifact/org.apache.pdfbox/pdfbox
    // compile group: 'org.apache.pdfbox', name: 'pdfbox', version: '2.0.15'

  @Test
    public void testImage() throws IOException {
        String path="c:\\pdfname.pdf"; 

        PDDocument document=PDDocument.load(new File(path));
        int numberOfPages = document.getNumberOfPages();
        PDPage page = document.getPage(0);


        PDResources pdResources = page.getResources();
        Iterable<COSName> iterable = pdResources.getXObjectNames();
        iterable.forEach(new Consumer<COSName>() {
            @Override
            public void accept(COSName t) {
                try {
                    System.out.println("###########################################");
                    System.out.println();
                    //is ImageXObject
                    if(pdResources.isImageXObject(t)){
                        System.out.println("COSName "+t.getName()+" isImageXObject");
                        PDXObject pdXObject = pdResources.getXObject(t);
                        PDImageXObject pdImageXObject=(PDImageXObject) pdXObject;
                        String suffix=pdImageXObject.getSuffix();
                        System.out.println("Height:"+pdImageXObject.getHeight()+"Width:"+pdImageXObject.getWidth()+"Suffix:"+suffix);
                        BufferedImage image=pdImageXObject.getImage();
                        ImageIO.write(image, suffix,
                                new File("C:\\Users\\admin\\Desktop\\aaa\\hello_"+UUID.randomUUID().toString()+"."+suffix));
                    }else{
                        System.out.println("COSName "+t.getName()+" isOtherXObject");
                    }
                    System.out.println();
                    System.out.println("###########################################");
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
