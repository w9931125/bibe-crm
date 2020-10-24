package com.bibe.crm.utils;

import com.bibe.crm.entity.vo.TreeData;

import java.util.ArrayList;
import java.util.List;

public class TreeUtil {


    /**
     * 将平行的树，转化为一颗有层级关系的树
     * @param list
     * @param pId
     * @return
     */
    public static List<TreeData> getTreeList(List<TreeData> list, Integer pId){
        if (list == null){
            return null;
        }
        //获取所有头节点
        List<TreeData> rootNode = new ArrayList<>();
        for (TreeData treeData: list ){
            if (treeData.getId().equals(pId)){
                rootNode.add(treeData);
            }
        }
        //头节点不存在的情况
        if (rootNode.size() == 0){
            return getChild(pId,list);
        }
        //头节点存在的情况
        for (TreeData treeData : rootNode){
            Integer id = treeData.getId();
            treeData.setChildren(getChild(id,list));
        }
        return rootNode;
    }
/*
    public static void main(String[] args) {
        List<TreeData> list=new ArrayList<>();
        TreeData a=new TreeData();
        a.setId(52);
        a.setPid(51);
        a.setTitle("王代理商1");
        TreeData b=new TreeData();
        b.setId(53);
        b.setPid(51);
        b.setTitle("王代理商2");
        TreeData c=new TreeData();
        c.setId(93);
        c.setPid(52);
        c.setTitle("销售一部");
        TreeData d=new TreeData();
        d.setId(94);
        d.setPid(52);
        d.setTitle("销售二部");
        list.add(a);
        list.add(b);
        list.add(c);
        list.add(d);
        List<TreeData> treeList = getTreeList(list, 51);

    }*/

    private static List<TreeData> getChild(Integer id,List<TreeData> list){
        //找到id节点子节点
        List<TreeData> childList = new ArrayList<>();
        for (TreeData treeData: list){
            if (treeData.getPid().equals(id)){
                childList.add(treeData);
            }
        }
        //给子节点设置子节点
        for (TreeData treeData : childList){
            id = treeData.getId();
            //递归
            treeData.setChildren(getChild(id,list));
        }
        if (childList.size() == 0){
            return null;
        }
        return childList;
    }

}
