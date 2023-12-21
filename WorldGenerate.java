package byow.Core;
import edu.princeton.cs.algs4.StdDraw;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import java.util.Random;

import java.util.Random;
public class WorldGenerate {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;

    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);


    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        int tileNum = RANDOM.nextInt(3);
        for (int i = 0; i < tileNum; i++) {
            int xcord = RANDOM.nextInt(0, 10);
            int ycord = RANDOM.nextInt(0, 10);
            int len = RANDOM.nextInt(0, 10);
            int bred = RANDOM.nextInt(0, 10);

            createRoom(world, xcord, ycord,len,bred);
        }
        ter.renderFrame(world);
    }

    public static void createRoom(TETile[][] world, int xc, int yc,int l, int b ) {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                if (x == xc && y == yc) {
                    for(int breadth=1;breadth<b;breadth++){
                        for(int length=1;length<l;length++){
                                world[x + breadth][y] = Tileset.FLOWER;
                                world[x + breadth][y + length] = Tileset.FLOWER;
                                world[x][y + length] = Tileset.FLOWER;
                        }
                    }
                    world[x][y]=Tileset.FLOWER;
                }
            }
        }
    }

}



