package query.video;

import entertainment.Season;
import fileio.ActionInputData;
import fileio.Input;
import fileio.MovieInputData;
import fileio.SerialInputData;
import filters.MovieFilters;
import filters.SerialFilters;
import utils.Sort;
import utils.WriterHelper;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import java.util.Map;
import java.util.stream.Collectors;

public class VideoDuration {

    public void queryMovieDuration(Input input, ActionInputData action, WriterHelper helper) throws IOException {
        MovieFilters filter = new MovieFilters(input.getMovies());
        Map<String, Integer> durations = new LinkedHashMap<>();
        List<MovieInputData> movies = filter.applyFilters(input, action);
        if (movies != null) {
            for (MovieInputData movie : movies) {
                Integer duration = movie.getDuration();
                durations.put(movie.getTitle(), duration);
            }
            List<String> longestMovie = Sort.sortByInteger(durations,action);
            String result = longestMovie.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(", ", "Query result: [", "]"));
            helper.addToArrayResult(action, result);
        } else {
            String result = "Query result: []";
            helper.addToArrayResult(action, result);
        }
    }

    public void querySerialDuration(Input input, ActionInputData action, WriterHelper helper) throws IOException {
        SerialFilters filter = new SerialFilters(input.getSerials());
        Map<String, Integer> durations = new LinkedHashMap<>();
        List<SerialInputData> serials = filter.applyFilters(input, action);
        if (serials != null) {
            for (SerialInputData serial : serials) {
                int totalDuration = 0;
                for(Season season:serial.getSeasons()) {
                    totalDuration += season.getDuration();
                }
                Integer duration = totalDuration;
                durations.put(serial.getTitle(), duration);
            }
            List<String> longestMovie = Sort.sortByInteger(durations,action);
            String result = longestMovie.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(", ", "Query result: [", "]"));
            helper.addToArrayResult(action, result);
        } else {
            String result = "Query result: []";
            helper.addToArrayResult(action, result);
        }
    }
}


