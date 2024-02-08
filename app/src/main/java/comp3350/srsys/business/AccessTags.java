package comp3350.srsys.business;

import java.util.List;

import comp3350.srsys.application.Services;
import comp3350.srsys.persistence.ITagPersistence;
import comp3350.srsys.objects.Tag;

public class AccessTags {

    private ITagPersistence dataAccess;

    public AccessTags() {
        dataAccess = Services.getTagPersistence();
    }

    public List<Tag> getAllTags() {
        return dataAccess.getAllTags();
    }
}
