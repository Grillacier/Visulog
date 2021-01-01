package up.visulog.gitrawdata;

public class CommitBuilder {
    private final String id;
    private String author;
    private String date;
    private String description;
    private String mergedFrom;
    private int modificationAdd;
    private int modificationDel;
    private int modificationAll;

    public CommitBuilder(String id) {
        this.id = id;
    }

    /**
      * Setter of modificationAdd and return it
      *
      *@param modificationAdd This parameter will be actual modificationAdd
      *
      *@return The current modificationAdd
    */
    public CommitBuilder setModificationAdd(int modificationAdd) {
        this.modificationAdd = modificationAdd;
        return this;
    }

        /**
          * Setter of modificationDel and return it
          *
          *@param modificationAdd This parameter will be actual modificationDel
          *
          *@return The current modificationDel
        */
    public CommitBuilder setModificationDel(int modificationDel) {
        this.modificationDel = modificationDel;
        return this;
    }
    /*CountModificationPerAuthor*/

        /**
          * Setter of modificationAll and return it
          *
          *@param modificationAdd This parameter will be actual modificationAll
          *
          *@return The current modificationAll
        */
    public CommitBuilder setTotal(int modificationAll) {
        this.modificationAll = modificationAll;
        return this;
    }

        /**
          * Setter of author and return it
          *
          *@param modificationAdd This parameter will be actual author
          *
          *@return The current author
        */
    public CommitBuilder setAuthor(String author) {
        this.author = author;
        return this;
    }
    /**
      * Setter of date and return it
      *
      *@param modificationAdd This parameter will be actual date
      *
      *@return The current date
    */
    public CommitBuilder setDate(String date) {
        this.date = date;
        return this;
    }
    /**
      * Setter of description and return it
      *
      *@param modificationAdd This parameter will be actual description
      *
      *@return The current description
    */
    public CommitBuilder setDescription(String description) {
        this.description = description;
        return this;
    }
    /**
      * Setter of mergedFrom and return it
      *
      *@param modificationAdd This parameter will be actual mergedFrom
      *
      *@return The current mergedFrom
    */
    public CommitBuilder setMergedFrom(String mergedFrom) {
        this.mergedFrom = mergedFrom;
        return this;
    }
    /**
    * Create a new commit with the current attribute of the object
    */
    public Commit createCommit() {
        return new Commit(id, author, date, description, mergedFrom, modificationAdd, modificationDel);
    }
}
