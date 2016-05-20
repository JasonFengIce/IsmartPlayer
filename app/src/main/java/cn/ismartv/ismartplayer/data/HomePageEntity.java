package cn.ismartv.ismartplayer.data;

import java.util.List;

/**
 * Created by huibin on 5/20/16.
 */


public class HomePageEntity {
    private List<Carousels> carousels;

    private List<Posters> posters;

    public void setCarousels(List<Carousels> carousels) {
        this.carousels = carousels;
    }

    public List<Carousels> getCarousels() {
        return this.carousels;
    }

    public void setPosters(List<Posters> posters) {
        this.posters = posters;
    }

    public List<Posters> getPosters() {
        return this.posters;
    }


    public class Expense_info {
        private int pay_type;

        private int cpid;

        private double price;

        private String cpname;

        private String duration;

        private double subprice;

        private String cptitle;

        public void setPay_type(int pay_type) {
            this.pay_type = pay_type;
        }

        public int getPay_type() {
            return this.pay_type;
        }

        public void setCpid(int cpid) {
            this.cpid = cpid;
        }

        public int getCpid() {
            return this.cpid;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public double getPrice() {
            return this.price;
        }

        public void setCpname(String cpname) {
            this.cpname = cpname;
        }

        public String getCpname() {
            return this.cpname;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getDuration() {
            return this.duration;
        }

        public void setSubprice(double subprice) {
            this.subprice = subprice;
        }

        public double getSubprice() {
            return this.subprice;
        }

        public void setCptitle(String cptitle) {
            this.cptitle = cptitle;
        }

        public String getCptitle() {
            return this.cptitle;
        }

    }


    public class Carousels {
        private String video_image;

        private String video_url;

        private Expense_info expense_info;

        private String content_model;

        private int pause_time;

        private String thumb_image;

        private String title;

        private String url;

        private String introduction;

        private boolean expense;

        private String model_name;

        public void setVideo_image(String video_image) {
            this.video_image = video_image;
        }

        public String getVideo_image() {
            return this.video_image;
        }

        public void setVideo_url(String video_url) {
            this.video_url = video_url;
        }

        public String getVideo_url() {
            return this.video_url;
        }

        public void setExpense_info(Expense_info expense_info) {
            this.expense_info = expense_info;
        }

        public Expense_info getExpense_info() {
            return this.expense_info;
        }

        public void setContent_model(String content_model) {
            this.content_model = content_model;
        }

        public String getContent_model() {
            return this.content_model;
        }

        public void setPause_time(int pause_time) {
            this.pause_time = pause_time;
        }

        public int getPause_time() {
            return this.pause_time;
        }

        public void setThumb_image(String thumb_image) {
            this.thumb_image = thumb_image;
        }

        public String getThumb_image() {
            return this.thumb_image;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitle() {
            return this.title;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUrl() {
            return this.url;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public String getIntroduction() {
            return this.introduction;
        }

        public void setExpense(boolean expense) {
            this.expense = expense;
        }

        public boolean getExpense() {
            return this.expense;
        }

        public void setModel_name(String model_name) {
            this.model_name = model_name;
        }

        public String getModel_name() {
            return this.model_name;
        }

    }

    public class Posters {
        private String vertical_url;

        private Expense_info expense_info;

        private String content_model;

        private int corner;

        private String title;

        private String url;

        private String introduction;

        private String poster_url;

        private boolean expense;

        private String model_name;

        private String custom_image;

        public void setVertical_url(String vertical_url) {
            this.vertical_url = vertical_url;
        }

        public String getVertical_url() {
            return this.vertical_url;
        }

        public void setExpense_info(Expense_info expense_info) {
            this.expense_info = expense_info;
        }

        public Expense_info getExpense_info() {
            return this.expense_info;
        }

        public void setContent_model(String content_model) {
            this.content_model = content_model;
        }

        public String getContent_model() {
            return this.content_model;
        }

        public void setCorner(int corner) {
            this.corner = corner;
        }

        public int getCorner() {
            return this.corner;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitle() {
            return this.title;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUrl() {
            return this.url;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public String getIntroduction() {
            return this.introduction;
        }

        public void setPoster_url(String poster_url) {
            this.poster_url = poster_url;
        }

        public String getPoster_url() {
            return this.poster_url;
        }

        public void setExpense(boolean expense) {
            this.expense = expense;
        }

        public boolean getExpense() {
            return this.expense;
        }

        public void setModel_name(String model_name) {
            this.model_name = model_name;
        }

        public String getModel_name() {
            return this.model_name;
        }

        public void setCustom_image(String custom_image) {
            this.custom_image = custom_image;
        }

        public String getCustom_image() {
            return this.custom_image;
        }

    }

}