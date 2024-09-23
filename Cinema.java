import java.util.ArrayList;
import java.util.List;

public class Cinema {
    private List<Theater> theaters;

    public Cinema() {
        this.theaters = new ArrayList<>();
    }

    public Cinema(List<Theater> theaters) {
        this.theaters = theaters;
    }

    public void listTheaters() {
        for (Theater theater : theaters) {
            System.out.println("Theater ID: " + theater.getTheaterId() + ", Address: " + theater.getAddress());
        }
    }

    public void addTheater(Theater theater) {
        theaters.add(theater);
    }

    public void listMoviesInAllTheater() {
        for (Theater theater : theaters) {
            theater.listMovies(); // Call listMovies for each theater
        }
    }

    public Theater selectTheater(int theaterId) throws TheaterNotFoundException {
        for (Theater theater : theaters) {
            if (theater.getTheaterId() == theaterId) {
                return theater;
            }
        }
        throw new TheaterNotFoundException("Theater not found with ID: " + theaterId);
    }

    public int getTotalTheaters() {
        return theaters.size();
    }
}
