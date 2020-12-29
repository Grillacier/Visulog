package up.visulog.gitrawdata;

public class CommitBuilder {
    private final String id;
    private String author;
    private String date;
    private String description;
    private String mergedFrom;
    private int modificationAdd;
    private int modificationDel;

    public CommitBuilder(String id) {
        this.id = id;
    }

    /*CountModificationPerAuthor*/
    public CommitBuilder setModificationAdd(int modificationAdd) {
        this.modificationAdd = modificationAdd;
        return this;
    }


    public CommitBuilder setModificationDel(int modificationDel) {
        this.modificationDel = modificationDel;
        return this;
    }
    /*CountModificationPerAuthor*/
    
    public CommitBuilder setAuthor(String author) {
        this.author = author;
        return this;
    }

    public CommitBuilder setDate(String date) {
        this.date = date;
        return this;
    }

    public CommitBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public CommitBuilder setMergedFrom(String mergedFrom) {
        this.mergedFrom = mergedFrom;
        return this;
    }

    public Commit createCommit() {
        return new Commit(id, author, date, description, mergedFrom, modificationAdd, modificationDel);
    }
}