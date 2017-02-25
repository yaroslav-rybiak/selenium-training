import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CollectImages {

    private WebDriver driver;

    @Before
    public void start() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void collectLinks() throws Exception {

        try (BufferedReader br = new BufferedReader(new FileReader("D:\\images\\links.txt"))) {
            String URL;
            int c = 1;
            while ((URL = br.readLine()) != null) {

                //open URL from file
                driver.get(URL);
                driver.findElement(By.cssSelector("a[data-mod=verticallist]")).click();

                //get a name of a model
                String name = driver.findElement(By.cssSelector("span[class=content-header]")).getAttribute("innerText");
                name = name.substring(name.lastIndexOf("/") + 1);

                //create a folder
                String counter = String.format("%03d", c);
                String folderName = "D:\\images\\" + counter + " " + name;
                Files.createDirectories(Paths.get(folderName));
                String timeStamp = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
                System.out.println(timeStamp + " " + counter + " " + name);
                c++;

                //create a list with links to images
                List<WebElement> linksList = driver.findElements(By.cssSelector("div[id=items-container] img"));
                List<String> imageList = new ArrayList<>();
                for (WebElement menuElement : linksList) {
                    imageList.add(menuElement.getAttribute("src"));
                }

                //print all links
                int imageName = 1;
                for(String link : imageList) {

                    // http://stackoverflow.com/questions/6813704/how-to-download-an-image-using-selenium-any-version
                    String prefix = String.format("%02d", imageName);
                    saveImage(link, folderName + "\\" + prefix + ".jpg");
                    imageName++;

                }
            }
        }
    }

    public static void saveImage(String imageUrl, String destinationFile) throws IOException {
        URL url = new URL(imageUrl);
        InputStream is = url.openStream();
        OutputStream os = new FileOutputStream(destinationFile);

        byte[] b = new byte[2048];
        int length;

        while ((length = is.read(b)) != -1) {
            os.write(b, 0, length);
        }

        is.close();
        os.close();
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }

}
