import java.io.Serializable;
import java.util.HashMap;
import java.util.Collection;
import java.util.HashSet;
import java.util.Date;

/** A commit represents a snapshot of the working directory at
 *  a specific point in time.
 *
 *  A commit contains a listing of the files found in a repo
 *  and pointers to copies of the versions that were current at the
 *  time of committal.  It also details which files are staged for
 *  commital or marked for removal.
 *
 *  A commit will inherit all of the snapshots found in the preceding
 *  commit of the commit's branch.
 *
 *  @author Joshua Leath
 */
public class Commit implements Serializable {
    /** The id of the commit that preceded this commit. */
    private int parentId;
    /** A mapping from filenames to their most current snapshots. */
    private HashMap<String, GitletObject> objects;
    /** The date this commit was pushed. */
    private Date commitDate;
    /** The message given to this commit by the user. */
    private String message;
    /** A unique id for this commit. */
    private int id;

    public Commit(int p) {
        parentId = p;
        objects = new HashMap<String, GitletObject>();
        if (ObjectManager.commitExists(parentId)) {
            Commit parent = ObjectManager.loadCommit(parentId);
            for (String name : parent.objects.keySet()) {
                GitletObject go = parent.objects.get(name);
                if (!go.isMarkedForRemoval()) {
                    objects.put(name, go);
                }
            }
        }
        commitDate = null;
        message = null;
        id = ObjectManager.numFilesInDir(".gitlet/commits");
    }

    /** Marks the file named FILENAME for committal with this commit.
     *  When a commit is pushed, it will back up new copies of the files
     *  that have been staged. This method assumes that the given 
     *  FILENAME exists in the working directory. */
    public void stageFile(String fileName) {
        // If this fileName is already found in this commit.
        if (objects.containsKey(fileName)) {
            GitletObject lastCommit = objects.get(fileName);
            Date lastMod = ObjectManager.getLastModifiedDate(fileName);
            if (lastMod.before(lastCommit.lastCommitDate())) {
                System.out.println("The file " + fileName + " has not been"
                        + " modified since the last commit.");
                return;
            }
        }
        int nextId = ObjectManager.numFilesInDir(".gitlet/obj");
        GitletObject newObj = new GitletObject(fileName, nextId);
        newObj.stage();
        objects.put(fileName, newObj);
    }

    /** Change the commitDate of GO to the current date. */
    public void updateCommitDate(GitletObject go) {
        go.setCommitDate(new Date());
        objects.put(go.getFileName(), go);
    }

    /** Marks a file in a commit for removal.  When the commit is pushed,
     *  this file will be removed from the working directory.  The
     *  snapshot of the file will NOT be removed and this commit will
     *  still reference it. Assumes that the give FILENAME exists in
     *  the working directory. */
    public void markForRemoval(String fileName) {
        if (objects.containsKey(fileName)) {
            GitletObject toRemove = objects.get(fileName);
            toRemove.unstage();
            toRemove.markForRemoval();
            objects.put(fileName, toRemove);
        }
        else {
            System.out.println("The file " + fileName + " has not been"
                    + " added to the repo.");
        }
    }

    /** Returns a collection of the GitletObjects in this commit
     *  that have been staged. */
    public Collection<GitletObject> getStagedFiles() {
        HashSet<GitletObject> result = new HashSet<GitletObject>();
        for (String name : objects.keySet()) {
            GitletObject curr = objects.get(name);
            if (curr.isStaged()) {
                result.add(curr);
            }
        }
        return result;
    }

    /** Returns a collection of the GitletObjects in this commit
     *  that have been marked for removal. */
    public Collection<GitletObject> getRemovedFiles() {
        HashSet<GitletObject> result = new HashSet<GitletObject>();
        for (String name : objects.keySet()) {
            GitletObject curr = objects.get(name);
            if (curr.isMarkedForRemoval()) {
                result.add(curr);
            }
        }
        return result;
    }

    /** Return the snapshot of the file associated with FILENAME in this
     *  commit. */
    public GitletObject getObject(String fileName) {
        return objects.get(fileName);
    }

    /** Return the date that this commit was pushed.  Returns null if
     *  the commit has not yet been pushed. */
    public Date getCommitDate() {
        return commitDate;
    }

    /** Returns the message that was associated with this commit by
     *  the user. */
    public String getMessage() {
        return message;
    }

    /** Sets the commit date of this commit to the Date D. */
    public void setCommitDate(Date d) {
        commitDate = d; 
    }

    /** Sets the message of this commit to the String MSG. */
    public void setMessage(String msg) {
        message = msg;
    }

    /** Returns a string containing relevant information about this
     *  commit in the following format:
     *
     *  ====
     *  Commit [COMMIT ID].
     *  [COMMIT DATE]
     *  [COMMIT MESSAGE]
     *
     */
    public String getLogInfo() {
        return "====\n" + "Commit " + id + "\n" + commitDate
            + "\n" + message + "\n\n";
    }

    /** Returns the id of this commit. */
    public int getId() {
        return id;
    }

    /** Returns the id of this commit's parent. */
    public int getParentId() {
        return parentId;
    }
}
