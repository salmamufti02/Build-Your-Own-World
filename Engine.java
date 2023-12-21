package byow.Core;
import java.util.ArrayList;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import java.util.List;
import java.util.Random;
import byow.TileEngine.Tileset;
import edu.princeton.cs.algs4.StdDraw;

public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    private long seed;
    private static Random random;
    private static TETile[][] world;

    public void generateRandomWorld() {
        world = new TETile[WIDTH][HEIGHT];
        fillWithNothing();

        List<Room> rooms = new ArrayList<>();  //initializes empty list called rooms to store generated rooms
        int numberOfRooms = 20; //creates 20 different rooms
        for (int i = 1; i <= numberOfRooms; i++) { //generates random rooms each time
            int roomWidth = RandomUtils.uniform(random, 8, 12); //random width for room for width between 5 and 10
            int roomHeight = RandomUtils.uniform(random, 13, 17); //room random height between 10 and 12
            int x = RandomUtils.uniform(random, WIDTH - roomWidth); //random x coord for room and ensures it doesnt  go beyond boundary
            int y = RandomUtils.uniform(random, HEIGHT - roomHeight);
            Room room = new Room(x, y, roomWidth, roomHeight); //new room obj with rand x, y
            rooms.add(room); //adds new room to list of rooms
        }

        for (Room room : rooms) {
            if (!checkoverlap(room)) { //if rooms are overlapping, we dont make the room
                createRoom(room);
                room.isroom=true;
            }
            else{
                room.isroom=false;
            }
        }
        makeHorizontalHallways(rooms);
        makeVerticalHallways(rooms);
        getWalls();
    }
    public static void makeHorizontalHallways(List<Room> rooms) {
        for(Room room:rooms){
            if(room.isroom){
                if(checknearestRoom(room,room.x-1,room.y,"minus")){ //if there is a room
                    int xcord=room.x-1;
                    int ycord=room.y;
                    while (world[xcord][ycord] == Tileset.NOTHING && xcord<WIDTH && ycord<HEIGHT && xcord>0 && ycord>0) {
                        world[xcord][ycord] = Tileset.FLOOR;
                        xcord -= 1;
                    }
                }
                else{
                    if(checknearestRoom(room,room.x+room.width+1,room.y,"plus")){
                        int xcord=room.x+room.width;
                        int ycord=room.y;
                        while (world[xcord][ycord] == Tileset.NOTHING && xcord<WIDTH && ycord<HEIGHT && xcord>0 && ycord>0) {
                            world[xcord][ycord] = Tileset.FLOOR;
                            xcord += 1;
                        }
                    }
                }
            }
        }
    }

    public static void makeVerticalHallways(List<Room> rooms) {
        for(Room room:rooms){
            if(room.isroom){
                if(checknearestRoom(room,room.x,room.y-1,"down")){ //if there is a room
                    int xcord=room.x;
                    int ycord=room.y-1;
                    while (world[xcord][ycord] == Tileset.NOTHING && xcord<WIDTH && ycord<HEIGHT && xcord>0 && ycord>0) {
                        world[xcord][ycord] = Tileset.FLOOR;
                        ycord -= 1;
                    }
                }
            }
        }
    }



    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        long seed = System.currentTimeMillis();
        random = new Random(seed);

        generateRandomWorld();

        ter.initialize(WIDTH, HEIGHT);
        ter.renderFrame(world);

    }

    public static void fillWithNothing() {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }


    public static boolean checkoverlap(Room room) {
        for (int i = room.x; i < room.x + room.width; i++) {
            for (int j = room.y; j < room.y + room.height; j++) {
                if (world[i][j] != Tileset.NOTHING) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void getWalls(){ //corner walls missing
        for (int x = 1; x < WIDTH; x++) {
            for (int y = 1; y < HEIGHT; y++) {
                if (world[x][y] == Tileset.FLOOR) {
                    if(world[x+1][y]==Tileset.NOTHING){
                        world[x+1][y]=Tileset.WALL;
                    }
                    if(world[x-1][y]==Tileset.NOTHING){
                        world[x-1][y]=Tileset.WALL;
                    }
                    if(world[x][y-1]==Tileset.NOTHING){
                        world[x][y-1]=Tileset.WALL;
                    }
                    if(world[x][y+1]==Tileset.NOTHING){
                        world[x][y+1]=Tileset.WALL;
                    }

                }
                }
            }
        //fillWallCorners();
    }

    //public void interactWithInputString(String arg) {

        /**
         * Method used for autograding and testing your code. The input string will be a series
         * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
         * behave exactly as if the user typed these characters into the engine using
         * interactWithKeyboard.
         * <p>
         * Recall that strings ending in ":q" should cause the game to quite save. For example,
         * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
         * 7 commands (n123sss) and then quit and save. If we then do
         * interactWithInputString("l"), we should be back in the exact same state.
         * <p>
         * In other words, running both of these:
         * - interactWithInputString("n123sss:q")
         * - interactWithInputString("lww")
         * <p>
         * should yield the exact same world state as:
         * - interactWithInputString("n123sssww")
         * <p>
         * //@param input the input string to feed to your program
         *
         * @return the 2D TETile[][] representing the state of the world
         */
    //}



    public void createRoom(Room room){
        for (int i = room.x; i < room.x + room.width; i++) {
            for (int j = room.y; j < room.y + room.height; j++) {
                //if (j == room.y || j == (room.y + room.height - 1) || i == (room.x + room.width - 1) || i == room.x) {
                //world[i][j] = Tileset.WALL;
                world[i][j] = Tileset.FLOOR;

            }
        }
    }

    public static boolean checknearestRoom(Room room,int xcord,int ycord, String sign){
        //int xcord=room.x-1;
        //int ycord=room.y;
        while (xcord<WIDTH-1 && ycord<HEIGHT-1 && xcord>0 && ycord>0) {
            if(world[xcord][ycord]!=Tileset.NOTHING){
                return true;
            }
            if(sign=="plus"){
                xcord+=1;
            }
            else if (sign=="minus"){
                xcord-=1;
            }
            else{
                ycord-=1;
            }
        }
        return false;
    }
    public TETile[][] interactWithInputString(String input) {
        char command = input.charAt(0);
        input = input.substring(1);

        if (command == 'N' || command == 'n') {
            StringBuilder seedst = new StringBuilder();
            for (char c : input.toCharArray()) {
                if (Character.isDigit(c)) {
                    seedst.append(c);
                } else {
                    break;
                }
            }
            seed = Long.parseLong(seedst.toString());
            random = new Random(seed);

            generateRandomWorld();
        }
        return world;
    }

}