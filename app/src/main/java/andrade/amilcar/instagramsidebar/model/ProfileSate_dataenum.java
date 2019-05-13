package andrade.amilcar.instagramsidebar.model;

import com.spotify.dataenum.DataEnum;
import com.spotify.dataenum.dataenum_case;

import java.util.List;

@DataEnum
public interface ProfileSate_dataenum {

    dataenum_case Loading();
    dataenum_case Loaded(List<GridItem> items);
    dataenum_case Error(Throwable e);
}
