import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author: Owen Astrachan
 * @date: September 24, 2026
 * @version 1.1, improved example from Fall 2025
 * @version 1.2, improved for Fall 2026
 * 
 * Train what is essentially an order-zero Markov model by counting 
 * word frequencies and then using these frequencies to calculate
 * maximum likelihood estimates for "unknown" texts
 */

public class AuthorShip {
    
    private Map<String, Integer> myProfile;
    private int myTotalWords;
    private final double ALPHA_SMOOTH = 0.1;
    
    public AuthorShip() {
        this.myProfile = new HashMap<>();
        this.myTotalWords = 0;
    }
    
    /**
     * Clean and tokenize text into words
     * @param is all text, e.g., from a file
     * @return list of white-space separated "words"
     */
    private List<String> tokenize(String text) {
        List<String> tokens = new ArrayList<>();
        String[] strs = text.split("\\s+");
        tokens.addAll(Arrays.asList(strs));
        return tokens;
    }
    
    /**
     * Build word frequency profile for author X from training folder
     */


    public void trainDirectory(String dirName) throws IOException {
        try (Stream<Path> paths = Files.walk(Paths.get(dirName))){
            List<Path> files = paths.filter(Files::isRegularFile)
                                    .collect(Collectors.toList());

            for(Path each : files) {
                String text = Files.readString(each);
                List<String> words = tokenize(text);
                myTotalWords += words.size();
                for(String word : words) {
                    myProfile.put(word, myProfile.getOrDefault(word,0)+1);
                }
            }
        }       
    }   
    
    /**
     * Calculate log-likelihood of text given author X's word distribution.
     * @param words is list of words whose likelihood determined 
     * based on stored/instance variable word counts
     * @return log likelihood of generating parameter
     */
    private double calculateLogLikelihood(List<String> words) {
        if (words.isEmpty()) {
            return Double.NEGATIVE_INFINITY;
        }
        
        double logLikelihood = 0.0;
        int vocabSize = myProfile.size();
        
        for (String word : words) {
            int wordCount = myProfile.getOrDefault(word, 0);
            double probability = 
                (double)(wordCount + ALPHA_SMOOTH) / (myTotalWords + ALPHA_SMOOTH*vocabSize);
            logLikelihood += Math.log(probability);
        }
        
        return logLikelihood;
    }

    /**
     * For all words in a file, calculated normalized likelihood, print stats
     * @param text words being compared to training data
     * Print statistics: normalized likelihood and total # words
     * @return normalized (by # words) likelihood
     */
    private double analyze(String text){
        List<String> words = tokenize(text);
        double likelihood = calculateLogLikelihood(words);
        double normalizedScore = likelihood/words.size();
        return normalizedScore;
    }

    /**
     * Called after training to analyze contents of unknown files.
     * Print statistics for each unknown file based on comparison
     * to trained author. Statistics printed in sorted order
     * by log likelihood
     * @param testFolder contains unknown authored files
     * @throws IOException if reading false
     */
    public void analyzeUnknowns(String testFolder) throws IOException {
        try (Stream<Path> paths = Files.walk(Paths.get(testFolder))){
            List<Path> files = paths.filter(Files::isRegularFile)
                                    .collect(Collectors.toList());

            HashMap<String,Double> map = new HashMap<>();
            for(Path each: files){
                String fileName = each.getFileName().toString();
                //System.out.printf("results for %s:\n",fileName);
                double value = analyze(Files.readString(each));
                map.put(fileName,value);
            }
            // sort map entries by log likelihood, with largest first
            ArrayList<Map.Entry<String,Double>> list = new ArrayList<>(map.entrySet());
            Collections.sort(list, Map.Entry.comparingByValue(Comparator.reverseOrder()));
            for(int k=0; k < list.size(); k++){
                System.out.printf("%1.2f\t%s\n",list.get(k).getValue(),list.get(k).getKey());
                System.out.println("-".repeat(30));
            }
         }
    }


    public static void main(String[] args) throws IOException{
        AuthorShip analyzer = new AuthorShip();
        
        // Example folder paths - replace with your actual paths
        String trainingFolder = "data/proust";  // Folder with Author X's known works
        String testFolder = "identify";        // Folder with texts to analyze
        
        System.out.printf("training on %s\n",trainingFolder);
        analyzer.trainDirectory(trainingFolder);
        analyzer.analyzeUnknowns(testFolder);
    }
}

