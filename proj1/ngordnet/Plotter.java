package ngordnet;
import com.xeiam.xchart.QuickChart;
import com.xeiam.xchart.Chart;
import com.xeiam.xchart.SwingWrapper;
import com.xeiam.xchart.StyleManager.ChartTheme;
import com.xeiam.xchart.ChartBuilder;
import java.util.List;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

public class Plotter {
    
    public static void plotAllWords(NGramMap ngm, String[] words, 
                                    int startYear, int endYear) {
        Chart chart = new ChartBuilder().width(800).height(600).xAxisTitle("years").yAxisTitle("data").build();
        int seriesAdded = 0;
        for (String word : words) {
            TimeSeries bundle = ngm.weightHistory(word, startYear, endYear);
            if (bundle.size() == 0) {
                System.out.println("No data for " + word);
                continue;
            }
            chart.addSeries(word, bundle.years(), bundle.data());
            seriesAdded = seriesAdded + 1;
        }   
        if (seriesAdded != 0) {
            new SwingWrapper(chart).displayChart(); 
        }
    }

    public static void plotCategoryWeights(NGramMap ngm, WordNet wn,
                       String[] categoryLabels, int startYear, int endYear) {
        Chart chart = new ChartBuilder().width(800).height(600).xAxisTitle("years").yAxisTitle("data").build();
        int seriesAdded = 0;
        for (String categoryLabel : categoryLabels) {
            Set words = wn.hyponyms(categoryLabel);
            TimeSeries bundle = ngm.summedWeightHistory(words, startYear, endYear);
            if (bundle.size() != 0) {
                chart.addSeries(categoryLabel, bundle.years(), bundle.data());
                seriesAdded = seriesAdded + 1;
            }
        }
        if (seriesAdded != 0) {
            new SwingWrapper(chart).displayChart();
        } else {
            System.out.println("No series added!");
        }
    }

    public static void plotCategoryWeights(NGramMap ngm, WordNet wn, 
            String categoryLabel, int startYear, int endYear) {
        Set words = wn.hyponyms(categoryLabel);
        TimeSeries summedWeightHistory = ngm.summedWeightHistory(words, startYear, endYear);
        plotTS(summedWeightHistory, "Popularity", "year", "weight", categoryLabel);
    }

    public static void plotCountHistory(NGramMap ngm, String word,
            int startYear, int endYear) {
        TimeSeries countHistory = ngm.countHistory(word, startYear, endYear);
        plotTS(countHistory, "Popularity", "year", "count", word);
    }

    /*
    public static void plotProcessedHistory(NGramMap ngm, int startYear,
            int endYear, YearlyRecordProcessor yrp) {
        //TODO LATER
    }
    */

    public static void plotTS(TimeSeries<? extends Number> ts, String title,
            String xlabel, String ylabel, String legend) {
        Collection years = ts.years();
        Collection counts = ts.data();

        Chart chart = QuickChart.getChart(title, ylabel, xlabel, legend, years, counts);
        new SwingWrapper(chart).displayChart();
    }

    public static void plotWeightHistory(NGramMap ngm, String word,
            int startYear, int endYear) {
        TimeSeries weightHistory = ngm.weightHistory(word, startYear, endYear);
        plotTS(weightHistory, "Popularity", "year", "weight", word);
    }

    /*
    public static void plotZipfsLaw(NGramMap ngm, int year) {
        //TODO later
    }
    */
}
