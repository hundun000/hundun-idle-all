package hundun.gdxgame.idleshare.gamelib.framework.data;

import hundun.gdxgame.idleshare.gamelib.framework.model.grid.GridPosition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Data;

/**
 * @author hundun
 * Created on 2021/11/10
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConstructionSaveData {
    public String prototypeId;
    private int level;
    private int workingLevel;
    private int proficiency;
    private GridPosition position;
}
