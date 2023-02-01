package com.netease.arctic.ams.server.model;

public class TreeNode {
  private long mask;
  private long index;

  public TreeNode() {
  }

  public TreeNode(long mask, long index) {
    this.mask = mask;
    this.index = index;
  }

  public long getMask() {
    return mask;
  }

  public void setMask(long mask) {
    this.mask = mask;
  }

  public long getIndex() {
    return index;
  }

  public void setIndex(long index) {
    this.index = index;
  }
}
