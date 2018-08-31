package BrandeisMap.utils;

public class CoordinateConverter {
    private static int MapWidthFeet = 5521;
    private static int MapHeightFeet = 4369;
    private static int MapWidthPixels = 2528;
    private static int MapHeightPixels = 2000;
    private static int CropLeft = 150;
    private static int CropDown = 125;

    public static int xToPixel(int x) {
        return  x * MapHeightPixels / MapHeightFeet;
    }

    public static int yToPixel(int y) {
        return  y * MapWidthPixels / MapWidthFeet;
    }

    public static int xToCroppedPixel(int x) {
        return (x * MapHeightPixels / MapHeightFeet) - CropLeft;
    }

    public static int yToCroppedPixel(int y) {
        return (y * MapWidthPixels / MapWidthFeet) - CropDown;
    }
}
