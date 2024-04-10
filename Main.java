import javax.swing.SwingUtilities;
import src.ui.SentimentAnalysisGUI;
public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SentimentAnalysisGUI();
        });
        // String text;
        // try {
        //     SentimentAnalysisClient client = new SentimentAnalysisClient();

        //     Scanner scanner = new Scanner(System.in);
        //     System.out.print("Enter text: ");
        //     text = scanner.nextLine();
        //     scanner.close();

        //     Map<String, String> sentimentResult = client.getSentiment(text);
        //     System.out.println(sentimentResult);
            
        //     System.out.print("Categorizing Sentiments : ");
        //     if (sentimentResult.get("label").equals("pos")) {
        //         System.out.println("Positive");
        //     } else if (sentimentResult.get("label").equals("neg")) {
        //         System.out.println("Negative");
        //     } else if (sentimentResult.get("label").equals("neu")) {
        //         System.out.println("Neutral");
        //     }

        // } catch (IOException | InterruptedException e) {
        //     System.out.println("Error: " + e.getMessage());
        // }
    }
}
