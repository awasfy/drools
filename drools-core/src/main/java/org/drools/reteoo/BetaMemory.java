/*
 * Copyright 2010 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.drools.reteoo;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import org.drools.common.Memory;
import org.drools.rule.ContextEntry;

public class BetaMemory
    implements
    Externalizable, Unlinkable, Memory {

    private static final long serialVersionUID = 510l;

    private LeftTupleMemory   leftTupleMemory;
    private RightTupleMemory  rightTupleMemory;
    private ContextEntry[]    context;
    
    /* Let's start with only right unlinked. */
    private boolean           isLeftUnlinked = false;
    private boolean           isRightUnlinked = true;
    
    // the node type this memory belongs to
    private short             nodeType;

    public BetaMemory() {
    }

    public BetaMemory(final LeftTupleMemory tupleMemory,
                      final RightTupleMemory objectMemory,
                      final ContextEntry[] context,
                      final short nodeType ) {
        this.leftTupleMemory = tupleMemory;
        this.rightTupleMemory = objectMemory;
        this.context = context;
        this.nodeType = nodeType;
    }

    public void readExternal(ObjectInput in) throws IOException,
                                            ClassNotFoundException {
        leftTupleMemory = (LeftTupleMemory) in.readObject();
        rightTupleMemory = (RightTupleMemory) in.readObject();
        context = (ContextEntry[]) in.readObject();
        isLeftUnlinked = in.readBoolean();
        isRightUnlinked = in.readBoolean();
        nodeType = in.readShort();
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject( leftTupleMemory );
        out.writeObject( rightTupleMemory );
        out.writeObject( context );
        out.writeBoolean( isLeftUnlinked );
        out.writeBoolean( isRightUnlinked );
        out.writeShort( nodeType );
    }

    public RightTupleMemory getRightTupleMemory() {
        return this.rightTupleMemory;
    }

    public LeftTupleMemory getLeftTupleMemory() {
        return this.leftTupleMemory;
    }

    /**
     * @return the context
     */
    public ContextEntry[] getContext() {
        return context;
    }

    public boolean isLeftUnlinked() {
        return this.isLeftUnlinked;
    }

    public boolean isRightUnlinked() {
        return this.isRightUnlinked;
    }

    public void linkLeft() {
        this.isLeftUnlinked = false;
    }

    public void linkRight() {
        this.isRightUnlinked = false;
    }

    public void unlinkLeft() {
        this.isLeftUnlinked = true;
    }

    public void unlinkRight() {
        this.isRightUnlinked = true;
    }
    
    public short getNodeType() {
        return this.nodeType;
    }
}
