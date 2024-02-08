package comp3350.srsys.persistence.stubs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import comp3350.srsys.objects.Tag;
import comp3350.srsys.persistence.ITagPersistence;

public class TagPersistenceStub implements ITagPersistence {

    private List<Tag> tagList;

    public TagPersistenceStub() {

        this.tagList = new ArrayList<>();

        //TAGS
        tagList.add(new Tag("Quiz"));
        tagList.add(new Tag("Note"));
        tagList.add(new Tag("Software Engineering"));
        tagList.add(new Tag("Science"));
        tagList.add(new Tag("Math"));
        tagList.add(new Tag("Biology"));
    }

    @Override
    public List<Tag> getAllTags() {
        return Collections.unmodifiableList(tagList);
    }

}
