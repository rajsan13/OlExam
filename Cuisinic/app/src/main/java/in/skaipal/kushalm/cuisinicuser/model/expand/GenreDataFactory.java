package in.skaipal.kushalm.cuisinicuser.model.expand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GenreDataFactory {
    public static ArrayList<MultiCheckGenre> makeMultiCheckGenres(ArrayList<String> arrayList, ArrayList<String> arrayList2, ArrayList<String> arrayList3, ArrayList<String> arrayList4, ArrayList<String> arrayList5, ArrayList<ArrayList<String>> arrayList6) {
        ArrayList<MultiCheckGenre> arrayList7 = new ArrayList();
        for (int i = 0; i < arrayList.size(); i++) {
            arrayList7.add(new MultiCheckGenre((String) arrayList.get(i), (String) arrayList2.get(i), (String) arrayList3.get(i), (String) arrayList4.get(i), (String) arrayList5.get(i), setItemsName((ArrayList) arrayList6.get(i))));
        }
        return arrayList7;
    }

    private static ArrayList<Artist> setItemsName(ArrayList<String> arrayList) {
        ArrayList<Artist> arrayList2 = new ArrayList();
        for (int i = 0; i < arrayList.size(); i++) {
            arrayList2.add(new Artist(((String) arrayList.get(i)).toString(), false));
        }
        return arrayList2;
    }

    public static List<Artist> makeRockArtists() {
        Artist artist = new Artist("Queen", true);
        Artist artist2 = new Artist("Styx", false);
        Artist artist3 = new Artist("REO Speedwagon", false);
        Artist artist4 = new Artist("Boston", true);
        return Arrays.asList(new Artist[]{artist, artist2, artist3, artist4});
    }

    public static List<Artist> makeJazzArtists() {
        Artist artist = new Artist("Miles Davis", true);
        Artist artist2 = new Artist("Ella Fitzgerald", true);
        Artist artist3 = new Artist("Billie Holiday", false);
        return Arrays.asList(new Artist[]{artist, artist2, artist3});
    }

    public static List<Artist> makeClassicArtists() {
        Artist artist = new Artist("Ludwig van Beethoven", false);
        Artist artist2 = new Artist("Johann Sebastian Bach", true);
        Artist artist3 = new Artist("Johannes Brahms", false);
        Artist artist4 = new Artist("Giacomo Puccini", false);
        return Arrays.asList(new Artist[]{artist, artist2, artist3, artist4});
    }

    public static List<Artist> makeSalsaArtists() {
        Artist artist = new Artist("Hector Lavoe", true);
        Artist artist2 = new Artist("Celia Cruz", false);
        Artist artist3 = new Artist("Willie Colon", false);
        Artist artist4 = new Artist("Marc Anthony", false);
        return Arrays.asList(new Artist[]{artist, artist2, artist3, artist4});
    }

    public static List<Artist> makeBluegrassArtists() {
        Artist artist = new Artist("Bill Monroe", false);
        Artist artist2 = new Artist("Earl Scruggs", false);
        Artist artist3 = new Artist("Osborne Brothers", true);
        Artist artist4 = new Artist("John Hartford", false);
        return Arrays.asList(new Artist[]{artist, artist2, artist3, artist4});
    }
}
