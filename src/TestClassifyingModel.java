import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.util.*;
import java.io.IOException;
import java.nio.file.*;

public class TestClassifyingModel {
    @Test
    public void testDoestoevsky() throws IOException{
        ClassifyingModel cm = new ClassifyingModel(1);
        String source = "data/dostoevsky";
        cm.trainDirectory(source);
        assertEquals(16919,cm.vocabularySize(),"vocab size not correct "+source);
        assertEquals(955589,cm.tokenSize(),"token size not correct "+source);
    }

    @Test
    public void testkafka() throws IOException{
        ClassifyingModel cm = new ClassifyingModel(1);
        String source = "data/kafka";
        cm.trainDirectory(source);
        assertEquals(10887,cm.vocabularySize(),"vocab size not correct "+source);
        assertEquals(137797,cm.tokenSize(),"token size not correct "+source);
    }

     @Test
    public void testDoestoevskyCaesar() throws IOException{
        ClassifyingModel cm = new ClassifyingModel(1);
        String source = "data/dostoevsky";
        cm.trainDirectory(source);
        Path path = Path.of("identify","caesar.txt");
        String text= Files.readString(path);
        double match = cm.calculateMatchProbability(text,0.1);
        String message = String.format("train = %s, match = %s",source,path);
        assertEquals(-71.43,match,1e-2,"match prob  "+message);
    }

    @Test
    public void testDoestoevskyMatches() throws IOException {
        String[] files = {"dumas-story.txt","gertrude.txt","prisonniere.txt","urteil.txt"};
        double[] results = {-183.49,-93.82,-152.80,-37.61};
        ClassifyingModel cm = new ClassifyingModel(1);
        String source = "data/dostoevsky";
        cm.trainDirectory(source);
        for(int k=0; k < files.length; k++){
            Path path = Path.of("identify",files[k]);
            String text= Files.readString(path);
            double match = cm.calculateMatchProbability(text,0.1);
            String message = String.format("train = %s, match = %s",source,path);
            assertEquals(results[k],match,1e-2,"match prob  "+message);
        }
    }
    
    @Test
    public void testShakespeareMatches() throws IOException {
        String[] files = {"dumas-story.txt","gertrude.txt","prisonniere.txt","urteil.txt"};
        double[] results = {-179.78,-89.16,-148.49,-35.91};
        ClassifyingModel cm = new ClassifyingModel(1);
        String source = "data/shakespeare";
        cm.trainDirectory(source);
        for(int k=0; k < files.length; k++){
            Path path = Path.of("identify",files[k]);
            String text= Files.readString(path);
            double match = cm.calculateMatchProbability(text,0.1);
            String message = String.format("train = %s, match = %s",source,path);
            assertEquals(results[k],match,1e-2,"match prob  "+message);
        }
    }

    @Test
    public void testNewAuthorsMatches() throws IOException {
        String[] files = {"cyrano.txt","goethe.txt","marlowe.txt","nietzsche.txt","voltaire.txt"};
        double[] results = {-73.80,-68.62,-64.30,-118.11,-62.33};
        ClassifyingModel cm = new ClassifyingModel(1);
        String source = "data/proust";
        cm.trainDirectory(source);
        for(int k=0; k < files.length; k++){
            Path path = Path.of("newauthors",files[k]);
            String text= Files.readString(path);
            double match = cm.calculateMatchProbability(text,0.1);
            String message = String.format("train = %s, match = %s",source,path);
            assertEquals(results[k],match,1e-2,"match prob  "+message);
        }
    }

}
