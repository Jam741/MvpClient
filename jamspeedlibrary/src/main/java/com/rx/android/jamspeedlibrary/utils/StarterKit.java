package com.rx.android.jamspeedlibrary.utils;

/**
 * Created by Jam on 2016/3/14.
 */
public final class StarterKit {

    //触发加载临界值
    private static int sLoadingTriggerThreshold = 1;
    //每页加载Item数量
    private static int sItemsPerPage = 20;
    //首次加载页面
    private static int sItemFristPage = 1;
    private static boolean sDebug;

    public static boolean isDebug() {
        return sDebug;
    }

    public static int getsLoadingTriggerThreshold() {
        return sLoadingTriggerThreshold;
    }

    public static int getsItemsPerPage() {
        return sItemsPerPage;
    }

    public static int getsItemFristPage() {
        return sItemFristPage;
    }

    /**
     * 构建创造者
     */
    public static class Builder {
        private int loadingTriggerThreshold = 1;
        private int itemPerPage = 20;
        private int itemsFirstPage = 1;
        private boolean debug;

        /**
         * 创建
         */
        public void build() {
            StarterKit.sLoadingTriggerThreshold = loadingTriggerThreshold;
            StarterKit.sItemsPerPage = itemPerPage;
            StarterKit.sItemFristPage = itemsFirstPage;
            StarterKit.sDebug = debug;
        }

        //设置加载临界阈值
        public Builder setLoadingTriggerThreshold(int loadingTriggerThreshold) {
            this.loadingTriggerThreshold = loadingTriggerThreshold;
            return this;
        }

        public Builder setItemsFirstPage(int itemFirstPage){
            this.itemsFirstPage = itemFirstPage;
            return this;
        }

        public Builder setItemsPerOage(int itemsPerPage){
            this.itemPerPage = itemsPerPage;
            return this;
        }

        public Builder setDebug(boolean debug){
            this.debug = debug;
            return this;
        }
    }


}
