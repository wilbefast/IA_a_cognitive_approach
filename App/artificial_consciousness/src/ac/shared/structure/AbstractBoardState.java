/**
 * 
 */
package ac.shared.structure;

import java.io.Serializable;

/**
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 28 mars 2012
 * @version 0.1
 */
public abstract class AbstractBoardState implements Serializable {
    private static final long serialVersionUID = 6794929662400698592L;

    protected long id;

    // TODO list of defining facts

    /**
     * Default constructor of an advanced boardstate
     * 
     * @param id
     *            the id's advanced boardstate
     */
    public AbstractBoardState(long id) {
        this.id = id;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

}
