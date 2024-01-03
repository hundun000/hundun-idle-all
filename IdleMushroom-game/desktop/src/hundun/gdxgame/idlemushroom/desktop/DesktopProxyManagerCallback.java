package hundun.gdxgame.idlemushroom.desktop;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter.OutputType;
import hundun.gdxgame.idlemushroom.IdleMushroomGame;
import hundun.gdxgame.idlemushroom.logic.ProxyManager.IProxyManagerCallback;

public class DesktopProxyManagerCallback implements IProxyManagerCallback {


    public Json jsonTool;

    DesktopProxyManagerCallback() {
        this.jsonTool = new Json();

        jsonTool.setOutputType(OutputType.json);
        jsonTool.setTypeName(null);
        jsonTool.setUsePrototypes(false);
    }

    @Override
    public void onProxyCauseExit(IdleMushroomGame game) {
        System.out.println(this.jsonTool.prettyPrint(game.getHistoryManager().getProxyRunRecords()));
    }
}
