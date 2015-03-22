import java.util.Date;
import java.io.Serializable;

/** A class representing a file in a gitlet repo.
 *
 *  A gitlet object represents a file at a specific point in time.
 *  The file itself is saved in the 'obj' directory of the gitlet
 *  repo.  A gitlet object itself stores no content, it is simply
 *  a description of a file that has been backed up and cached in
 *  the repo.
 *
 *  @author Joshua Leath
 */
public class GitletObject implements Serializable {
    /** The date that this object was last committed in the repo. */
    private Date lastCommit;
    /** A boolean representing whether the variable has been staged. */
    private boolean staged;
    /** A boolean representing whether the variable has been marked
     *  for removal. */
    private boolean markedForRemove;
    /** A string representing the name of the file that this object
     *  is associated. */
    private String fileName;
    /** An id number to distinguish different GitletObjects. */
    private int id;

    public GitletObject(String fileName, int id) {
        this.fileName = fileName;
        this.id = id;
        staged = false;
        markedForRemove = false;
        lastCommit = null;
    }

    /** Returns true if this object has been committed
     *  before, else false. */
    public boolean hasBeenCommitted() {
        return lastCommit != null;
    }

    /** Returns true if this object is staged to be committed,
     *  else false. */
    public boolean isStaged() {
        return staged;
    }

    /** Marks this object as staged. */
    public void stage() {
        staged = true;
    }

    /** Unstages this object. */
    public void unstage() {
        staged = false;
    }

    /** Unmarks this object for removal. */
    public void unmarkForRemoval() {
        markedForRemove = false;
    }

    /** Marks this object for removal. */
    public void markForRemoval() {
        markedForRemove = true;
    }

    /** Returns true if this object has been marked for removal,
     *  else false. */
    public boolean isMarkedForRemoval() {
        return markedForRemove;
    }

    /** Returns the date that this object was last committed,
     *  returns null if it has not been committed before. */
    public Date lastCommitDate() {
        return lastCommit;
    }

    /** Sets the Date representing the last commit date of
     *  this object to be DATE. */
    public void setCommitDate(Date date) {
        lastCommit = date;
    }

    /** Returns a string representing the name of the file
     *  this object is associated with. */
    public String getFileName() {
        return fileName;
    }

    /** Returns the id of this object. */
    public int getId() {
        return id;
    }
}
