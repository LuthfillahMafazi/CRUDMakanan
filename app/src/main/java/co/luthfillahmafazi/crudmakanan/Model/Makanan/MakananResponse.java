package co.luthfillahmafazi.crudmakanan.Model.Makanan;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MakananResponse {
    @SerializedName("result")
    private int result;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List <MakananData> data;


    @SerializedName("url")
    private String url;

    @SerializedName("name")
    private String name;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public List<MakananData> getData() {
        return data;
    }

    public void setData(List<MakananData> data) {
        this.data = data;
    }
}
