/*
 *  Copyright (c) 2018 Cerus
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 * Cerus
 *
 */

package hundun.gdxgame.bugindustry.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.BitmapFontLoader;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class FontUtil {

    public static BitmapFont KOMIKA = null;
//    public static BitmapFont KOMIKA_20 = null;
//    public static BitmapFont KOMIKA_15 = null;
//    public static BitmapFont KOMIKA_10 = null;
//    public static BitmapFont KOMIKA_8 = null;

    private FontUtil() {
        throw new UnsupportedOperationException();
    }

    public static void init() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/KOMTITK_.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 30;
        KOMIKA = generator.generateFont(parameter);
        KOMIKA.getData().markupEnabled = true;
        KOMIKA.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        
//        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
//        parameter.size = 20;
//        KOMIKA_20 = generator.generateFont(parameter);
//
//        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
//        parameter.size = 15;
//        KOMIKA_15 = generator.generateFont(parameter);
//
//        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
//        parameter.size = 10;
//        KOMIKA_10 = generator.generateFont(parameter);
//
//        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
//        parameter.size = 8;
//        KOMIKA_8 = generator.generateFont(parameter);
    }
}
