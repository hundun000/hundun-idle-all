package hundun.gdxgame.idleshare.gamelib.framework.model.grid;

import hundun.gdxgame.gamelib.base.util.JavaFeatureForGwt;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GridPosition {

    int x;
    int y;

    public String toShowText()
    {
        return JavaFeatureForGwt.stringFormat("({0},{1})", x, y);
    }

}
