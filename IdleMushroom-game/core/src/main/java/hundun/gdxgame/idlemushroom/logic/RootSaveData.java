package hundun.gdxgame.idlemushroom.logic;


import hundun.gdxgame.gamelib.starter.save.IRootSaveExtension;
import hundun.gdxgame.idleshare.gamelib.framework.data.GameplaySaveData;
import hundun.gdxgame.idleshare.gamelib.framework.data.SystemSettingSaveData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hundun
 * Created on 2023/02/16
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RootSaveData {
    GameplaySaveData gameplaySave;
    SystemSettingSaveData systemSettingSaveData;
    
    public static final class RootSaveExtension implements IRootSaveExtension<RootSaveData, SystemSettingSaveData, GameplaySaveData> {

        public static final RootSaveExtension INSTANCE = new RootSaveExtension();
        
        @Override
        public SystemSettingSaveData getSystemSave(RootSaveData rootSaveData) {
            return rootSaveData.getSystemSettingSaveData();
        }

        @Override
        public GameplaySaveData getGameplaySave(RootSaveData rootSaveData) {
            return rootSaveData.getGameplaySave();
        }

        @Override
        public RootSaveData newRootSave(GameplaySaveData gameplaySave, SystemSettingSaveData systemSettingSave) {
            return new RootSaveData(gameplaySave, systemSettingSave);
        }

        @Override
        public GameplaySaveData newGameplaySave() {
            return new GameplaySaveData();
        }

        @Override
        public SystemSettingSaveData newSystemSave() {
            return new SystemSettingSaveData();
        }
        
    }
}
