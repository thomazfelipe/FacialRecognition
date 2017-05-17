package bests.pi.facialrecognition.ArtificialIntelligence;
import static  com.googlecode.javacv.cpp.opencv_highgui.*;
import static  com.googlecode.javacv.cpp.opencv_core.*;
import static  com.googlecode.javacv.cpp.opencv_imgproc.*;

import java.io.File;
import java.io.FilenameFilter;
import com.googlecode.javacv.cpp.opencv_contrib.FaceRecognizer;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import com.googlecode.javacv.cpp.opencv_core.MatVector;

/**
 * Created by thomaz on 15/05/17.
 */

public class Trainee {
//    FaceRecognizer faceRecognizer;
//    MatVector images;
//    int count = 0;
//
//    public Trainee (){
//        faceRecognizer =  com.googlecode.javacv.cpp.opencv_contrib.createLBPHFaceRecognizer(2,8,8,8,200);
//    }
//    public boolean trainee() {
//
//        File root = new File(mPath);
//
//        FilenameFilter jpgFilter = (dir, name) -> name.toLowerCase().endsWith(".jpg");
//
//        File[] imageFiles = root.listFiles(jpgFilter);
//
//        MatVector images = new MatVector(imageFiles.length);
//
//        int counter = 0;
//
//        IplImage img;
//        IplImage grayImg;
//
//        for (File image : imageFiles) {
//            String p = image.getAbsolutePath();
//            img = cvLoadImage(p);
//
//            int i2=p.lastIndexOf("-");
//            int i3=p.lastIndexOf(".");
//            int icount=Integer.parseInt(p.substring(i2 + 1, i3));
//
//            if (count<icount) {
//                count++;
//            }
//
//            grayImg = IplImage.create(img.width(), img.height(), IPL_DEPTH_8U, 1);
//            cvCvtColor(img, grayImg, CV_BGR2GRAY);
//            images.put(counter, grayImg);
//            counter++;
//        }
//        int[] test = {0,1};
//        if (counter>0) {
//            //  if (labelsFile.max()>1)
//            faceRecognizer.train(images, test);
//        }
//        return true;
//    }
}
