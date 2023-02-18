package hundun.gdxgame.idleshare.core.framework;

import com.badlogic.gdx.Gdx;

import hundun.gdxgame.gamelib.base.IFrontEnd;
import hundun.gdxgame.gamelib.base.util.JavaFeatureForGwt;

/**
 * @author hundun
 * Created on 2023/02/21
 */
public class LibgdxFrontend implements IFrontEnd {

    @Override
    public String[] fileGetChilePathNames(String folder) {
        return null;
    }

    @Override
    public String fileGetContent(String string) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void log(String logTag, String format, Object... args) {
        Gdx.app.log(logTag, JavaFeatureForGwt.stringFormat(format, args));
    }

}
