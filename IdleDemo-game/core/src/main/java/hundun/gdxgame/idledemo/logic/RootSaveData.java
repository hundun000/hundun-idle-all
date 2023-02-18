package hundun.gdxgame.idledemo.logic;


import hundun.gdxgame.gamelib.starter.save.StarterSaveHandler.IFactory;
import hundun.gdxgame.idleshare.gamelib.framework.data.GameplaySaveData;
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
    
    public static final class Factory implements IFactory<RootSaveData, Void, GameplaySaveData> {

        public static final Factory INSTANCE = new Factory();
        
        @Override
        public Void getSystemSave(RootSaveData rootSaveData) {
            return null;
        }

        @Override
        public GameplaySaveData getGameplaySave(RootSaveData rootSaveData) {
            return rootSaveData.getGameplaySave();
        }

        @Override
        public RootSaveData newRootSave(GameplaySaveData gameplaySave, Void systemSettingSave) {
            return new RootSaveData(gameplaySave);
        }

        @Override
        public GameplaySaveData newGameplaySave() {
            return new GameplaySaveData();
        }

        @Override
        public Void newSystemSave() {
            return null;
        }
        
    }
}
