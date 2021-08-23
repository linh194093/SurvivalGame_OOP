package rpg.world;

public class WorldMap {
    private Map worldMap[];

    public WorldMap() {
        worldMap = new Map[4];
        initMap();
    }

    private void initMap() {
        worldMap[0] = new Map("src/assets/map0.txt");
        worldMap[0].initPort(736, 352, -99, -99);
        worldMap[0].setId(0);
        worldMap[1] = new Map("src/assets/map1.txt");
        worldMap[1].initPort(25, 160, 768, 383);
        worldMap[1].setId(1);
        worldMap[2] = new Map("src//assets/map2.txt");
        worldMap[2].initPort(10, 250, 352, 608);
        worldMap[2].setId(2);
        worldMap[3] = new Map("src//assets/map3.txt");
        worldMap[3].initPort(320, 0, -99, -99);
        worldMap[3].setId(3);
    }

    public Map getMap(int i) {
        return worldMap[i];
    }
}
