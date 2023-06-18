package bomoncntt.svk62.mssv2051067158.data.remote.mapper;

import android.content.Context;
import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

import bomoncntt.svk62.mssv2051067158.domain.models.Dish;
import bomoncntt.svk62.mssv2051067158.utils.Constraints;
import bomoncntt.svk62.mssv2051067158.utils.FileIOHelper;
import bomoncntt.svk62.mssv2051067158.utils.ViewHelper;

public class DishDto {
    @SerializedName("DishID")
    private int dishID;
    @SerializedName("DishName")
    private String dishName;
    @SerializedName("Price")
    private double price;
    @SerializedName("ImageBase64")
    private String imageBase64;

    public DishDto(int dishID, String dishName, double price, String imageBase64) {
        this.dishID = dishID;
        this.dishName = dishName;
        this.price = price;
        this.imageBase64 = imageBase64;
    }

    public static DishDto mapToDto(Dish dish){
        return new DishDto(
                dish.getDishID(),
                dish.getDishName(),
                dish.getPrice(),
                "iVBORw0KGgoAAAANSUhEUgAAAEAAAABACAYAAACqaXHeAAAACXBIWXMAAAHYAAAB2AH6XKZyAAAAGXRFWHRTb2Z0d2FyZQB3d3cuaW5rc2NhcGUub3Jnm+48GgAADhZJREFUeJy1m2uQXMV1x399X/Pamd3R7Gol7QpJ6I0USSsEtkxhIhNXxY6dD3aZVBzjBAiGclyUCU7iBNslYTkmcTkqJ04IwcQQh4JgRYFQtmODIkCC8DBCCCGEXqvHrlba58zOzuvOvd35MPucuXd2dnb3XzUfpvv0uafP7T59zulzBfMIpX4ctNPySRA3CaXeUpr6F6vhjr1CCOVNr4Q98q+fE5I7lRDXgnrRimqfF+K2/HzJKOaLMYA9/KPbFeLRyW0KdVAa5u+Hw3/YPbk9m32kXXO0pwTcUCbgbVbsjsfmS0ZtvhgDKMWy8jaBuFF3nNdyyUdWjLXlko+s0B3t9fLJj/JYPp8yGvPJXGgipjwXO+2apr1ipx99AIRQSn0TWOzJQ6jYfMpYtwIyfXsWKz1zqyXoMYk8J+L3JstplBIrwVsDwGKleKhKf4kH2irPdvXjYDElN0pDNAVy6nXRckd6xpOgThugBnbGcm6+V2EHRoVE04LdhhbcbS34xj8DZLOPt+mOcwJoqOcZk5BxDbkuHL6zC6A4/KMbJOJeBJ9AER6lOW5FM1uFuKcwU+Z1KSA7sPtryh38nidDLTSiWyueFojfUdBaD/8KntCr4H8QbESx1YtGCj4Wit5xYKa869oCUhMvCNenU9EA4vbqC3tmULAQ+GKV3SKl5pyoh3ddp0BD/P4jQgt67zkxr3bVD69FInf11DOw/mNQGGe9O2RNwxUSJXOlX41jfEURvFX32OkI1NCepqxDKNJy77iGldoZzvUX+pUqhCoZauiBdSC8WCuUm0a6gyiZmTpOi6DpCYQenfksFG9bMedDQtxVHG/qezRKs1aczov0VEBm6Lsdmiw8pGRhs1J2ENSopQ8MIPQuZHGN1+THoJvtCL1xqozKRRa7UHKk6lyEFkUz2xBCr0pXAcXbQuOggnUobgRCgAscB/FzRy/+wGubVCggP/DA96Qcvk8pWbebLNAQRjOgl2RQRZRMo5RT23hhILQoCBOEiRj9Iax6RQIYRsjbAtE7902VtQyZvj9zUO4M1T8zKKWRsRdiuyUnz9JTRKw+hKhuCwQaaAEQITQtjNDCJSXVCiEuBKK3T3HPPUy2JmG+FCDoG7mGK8MdODI4pcfQcrTGjtDScBw/71AhQeaAHK47WOKoBRFaFKE3IkSg+uOVqnCUKhQgjNhf4KQfVMqe1Xobg+sKhrMWI9kAqeINOLR50jkyRHdyO5eTS4lbrxAJFmkI2Zhm9VWhZB4l8+D0IbQwQo+j6TE8DrgPpJB3lzf67nN7cPfdjsrtVm4uMe0sAaXgyqDJqQshznZHMK0oqUyATM5EKVizqp1lbQtrYcW5i72cOtsFQDDgkIjlSTQWaGnK0t6SoSWewyelMDopHWEkEEYCTejPCyV3mbE/fsWbdhrkBnY/LN2hL3kty1xe8F5niHdPhXi/M0ihWGLXGGsgGgmP04XDQT6y7Rrvk9EDSileffM42Zy3a2+akuWLhlnVlmJVe4pgwMu4CnRjwT8EF9x/T7Vn1SRSbuA735TuwANj/892Bzj4doSjp8I4Hi5xIh4jFJzY4yuXL+bqZZ7Rri/OnOvh7PnpnTtdk6xemuLadb0saZ7wLTSt+f5Q81/99XTjaz7qcgM73+i6nLtu7/4mznRVNzYLGmOEwxMK6Ni4kuZEY5URlegfSPH2sTMzGrNiyTA3dXTTmjBfDyV2friWMTU77s8dSuze/5rzrOtOH+aUUxjGzA+VesZ0XorR3R/h49cP31frmJpjgUNvmx+uZfIlTKUr2LU5QFPHFKcn8oBt6/zsUFNLrfQ1K0CggtNTlaDK8mBDqZknawaH6krwACCEVrN3NC9J0fJ10nNlkKJT+yooFh0u9w3NrVA+mBcFuO7Uo8FxXE6c6pom+1eCAt4/dRHH63iZB8xL9iKbzZOIhVkcbyAWMokFTRpDJkZ+GDtYPckbLAyzZWGATFMzgxmbgZECAxkbp2b7MzPUrACFbxIMU9O4OhFm3cIoyxeEaG8M0dQYgYZIBW1W2nTnDQYdgatKp7AuFAsMRXvAJdQYgIVTj1mlFKmcQ/9IgQuDWc72ZbiUzOKTckcpal4+0ydElBLs3/fZcynnyw++cek3XVWS2tIFHW1NXNvexMZFUUy9bDdFIhCoHk7YoxG3pc387eaLLp39Gd7tSnHyShpXlnjomlB3bW5+oSViPsy2Hfv8ruHGUFUB6sABA2foCeAWgBNDed64nKWtOcb2ZU2ErSoLqLERypUyT8gUHI51p+jtT/MbzSGWN40pXj1F58AXxC23+K6I6gr41b5vIdg1pVE3IFo91d+dTPPtZ14iGg6yrDXB2qWLuHnrejStNsdTSsX+w+/zwcXLnL8yQDqb51tf/DRLmpuqDxxOQ8VpI74hrtvxHb8hvq9Q/fphkyHureioIaIpuJIryWEu9A7y3rlL/Pz1d1mcaGTjCu9QuBzHz19iz97nx/8HLIN8sYZj1DsP+afqwIG/ETt2eDLwnY36370bcLVjFR2WBeGwx4hJME3sUIj/OngYV0pi4SCf/NCmmleAKxW/eP0oI3kb09D49PYtWLW4xiMZsO3KdiHWi207PO8N/Depq9WfqxaCVCbL4798lcMnL/Cp7ZtrnjyArgk+tX0zb57o5NGfHWI4k6tbFKDSNZ0EfwXE+04jqHTH/HlNQEoct6S/ojux8t46eZ6/37d/PDbY+9Kv2ftyKaVfsB0e+u8XOXzy/Di9M+pQFV0XZA3P9ZZtkBHhG1b62gCx7a6ieuE/94B4YEqHnwIkMJCFZAGKiiYBrQ0R1rVNXA++dOQDXjj8PpuubmfzyqX82y//D4CbO9Zz5PQFnnv1HfKFIlvXlPKWa5csIp3KEj/RB7IfAjosjEF7HHSPFeUt2/f99j9Mdwo8/bRO3HwC1O+NN2oCYh6x/cURSHvsv7AFm9tA13jxnZP87ZO/QNcElmmSK5ToQwELu1jElYqvf/4TfHTTGpASjnRD1oNnIgLrF1W2DyXLlfAknf231n0Mwqgj9MIznwH5BRBXI+gnnrgR6U5EXAUXzqT8maxthZbS0fnvz7/GvoOHKRQdrl9XKhJ540QnAdPgsx/dyh/81mgeozcNJ3v9eV67FEKTHC2pekmm3gOVQIgzuPInXP+xZ2blCPlBHXn5GEVnw3iD7cLpKgpY1wrNE76DlArHdbHM0g60iw6Grk81lH0j8MEVf55br4LwlKj3H8XKjq/McCp1RoOm8diU/5YOMR+3tyEA8akxgaaJ8ckDWKZReUosCEPEh2dLQ/nkQVM/rUX0ctS3AtQBg8PkkMqY1Aj9BUjloShLbnBLFK6Kg1nnPUvRhQuDMJAp8QwY0BqFtqaSLZqYxVlWbFktprta8kDd93/q6KH/oGDfUpV1Uwy0OYoHFP7SKnG3WLXl4XrY1i+dHfwSmqjinyooeFjweuH/qk6jIo/Vy7ZuBYht21IY1u6qRIXCdEVgs4cmviJWr55xcdQYZl0pqt45eBy7uN6XIBSEkG8pwWxRl+WfjNlvUNnwEQzdv+ohny85NXOPQ8iGmvP/fpi1AkRHRxLYiq55L0NFKUqb072g3sXQf3c2S38Mc2KiRcdNp9D0TRi6dzLfcSA7y4huAq9gGDeJZZvmJG8+ZzkrseXGkyCvwjCPexLkC6XfbKDUD5ENN8/V5GGO7wVEx47kkxfVl99MCmV72ddstnQyzBB5KTiSJPtyJvx3c7HsJ2NOvxd44tn9n8sX3KeUKnm2a6KCLXGdSLkjGA5DcJpyFiDrKnpsiz5bQwJKqctKqN++cevGd+ZK5jlTwOPPvrjDKRT3q9G0+fgDBCwOQrMosKopSDxY0oa0LLSGcIUI/ekC5wYy2MEohCqTr1LJpKPk9h3bNtVVGluOOVHAV3fuaYo3Nve2LWmtuJTUNI2GSJCRkRwXLvUS0KE5ZBIPGjSELXJuKYk6nCsykClgO5LNG1bS4lNPoJTi6PHOM87Q2Q333DPz6vByzMnVmHTNX13s6TOz+SKrr26f0hcKmggg2hCifVEzXT39dKdtutM2kK3gtWHtsqqTP3biHL39yZUQ3wV8fbayz9oIfnXXQ7dmbfs6gIGhJGfOdY33GbqOoU8YgMZYhCWtC3x5rVnVzpJF3jVZCjhx6iKXe8cOAPG1nXt+4lk6PxPMWgFFx3lwso/TN5Dk4qVSIsP0CIPjTVEWLaxUwqoVS6pWkZ0+201XT//kJh3EX9Yr9xhmpYD7dj3y8UKhuKS8vbunj1R6BNPw3mGJeGxKzdCytoWsuMojxzeKsxcuc+6iZ3boM9/+wROrZyz4JMxKAbZr7/LrO3e+h8Gkf5qstTlOIh5jyaIEq1e1+9Jd7O7jTOclv27NVfxRjeJ6M5jNYKfobPLrswIml3uHSKb846QVyxZzzZqrfI+int5BPjjT5dM7CqVurkVWP9StgD+5/4fLitKtLAAYhTlq/Lov9zOcrrT2gYBFU2ME4XPX2Nef4r0T56pd6oxh23f/6Yl4zYKXoW4FmJb5yWoBnjEp6dnV00tmUjBkBUwWxCO+b34wmebo+501XUIBeqEo/fMR06BuBSilVlTr1ya9WaXgQlcf2Wwey9RJNDYgfKY/nM5w5NgZ5AxyCMIVdX+dVr8CkEurEpTNTyrJQGqYeDyK8LkoHcnkOPzuaVx3hgkUTdVWhe01tP6BompIWr58Q0GLjg0r0X2yxNlcgcNHT1Ms1lEdprTq3+FUQd0KsJF/HgiYXX5LOTep0jtgWc51W9YWLMu7frFQsNXho6fqqQ61gZ+qlFnXpQjA/wOFim57NFFYeQAAAABJRU5ErkJggg=="
        );
    }

    public Dish mapFromDto(Context context){
        FileIOHelper fileIOHelper =  FileIOHelper.getInstance(context);
        Bitmap bitmap = ViewHelper.convertStringToBitmap(imageBase64);

        String imagePath = "";
        if(bitmap != null){
          imagePath = fileIOHelper.copyImageToApplication(bitmap, Constraints.FOOD_IMAGES_DIR).toString();
        }

        return new Dish(
                dishID,
                dishName,
                price,
                imagePath
        );
    }

    public int getDishID() {
        return dishID;
    }

    public void setDishID(int dishID) {
        this.dishID = dishID;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }
}
