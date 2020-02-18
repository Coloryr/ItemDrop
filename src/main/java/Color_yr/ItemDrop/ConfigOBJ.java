package Color_yr.ItemDrop;

import java.util.ArrayList;
import java.util.List;

public class ConfigOBJ {
    private String version;
    private int DropProbability;
    private int NeedItem;
    private int CostItem;
    private String Item;
    private List<String> NBT;

    public ConfigOBJ() {
        this.version = "1.0.0";
        this.DropProbability = 50;
        this.Item = "Stone";
        this.NBT = new ArrayList<>();
        this.NeedItem = 10;
        this.CostItem = 3;
    }

    public int getCostItem() {
        return CostItem;
    }

    public int getNeedItem() {
        return NeedItem;
    }

    public int getDropProbability() {
        return DropProbability;
    }

    public List<String> getNBT() {
        return NBT;
    }

    public String getItem() {
        return Item;
    }

    public String getVersion() {
        return version;
    }
}
