/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package inte.com.camel.activemq.camel.business;

import static org.junit.Assert.*;

import java.util.Date;

import inte.com.BaseTest;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.camel.activemq.camel.business.IDemoBusiness;
import com.camel.framework.utils.DateUtils;

/**
 * 
 * @author dengqb
 * @date 2013年12月26日
 */
public class DemoBusinessTest extends BaseTest {
    @Autowired
    private IDemoBusiness demoBusiness;
    
    @Test
    public void testSendMessage() {
        System.out.println("start time:"+DateUtils.getStandardDate(new Date()));
        String body = "{\"trade_fullinfo_get_response\":{\"trade\":{\"adjust_fee\":\"0.00\",\"alipay_id\":2088802275901707,\"alipay_no\":\"2014010111001001700010585550\",\"available_confirm_fee\":\"119.70\",\"buyer_alipay_no\":\"13756295425\",\"buyer_area\":\"吉林长春联通\",\"buyer_cod_fee\":\"0.00\",\"buyer_email\":\"1146670151@qq.com\",\"buyer_nick\":\"wenda117\",\"buyer_obtain_point_fee\":0,\"buyer_rate\":false,\"cod_fee\":\"0.00\",\"cod_status\":\"NEW_CREATED\",\"commission_fee\":\"0.00\",\"created\":\"2014-01-01 02:34:26\",\"discount_fee\":\"0.00\",\"eticket_ext\":\"\",\"has_post_fee\":true,\"has_yfx\":false,\"is_3D\":false,\"is_brand_sale\":false,\"is_force_wlb\":false,\"is_lgtype\":false,\"is_part_consign\":false,\"modified\":\"2014-01-01 02:34:55\",\"num\":3,\"num_iid\":36452304368,\"orders\":{\"order\":[{\"adjust_fee\":\"0.00\",\"buyer_rate\":false,\"cid\":50012983,\"discount_fee\":\"213.00\",\"is_daixiao\":false,\"is_oversold\":false,\"num\":3,\"num_iid\":36452304368,\"oid\":501699862933397,\"order_from\":\"WAP\",\"outer_iid\":\"1005A\",\"part_mjz_discount\":\"0.00\",\"payment\":\"119.70\",\"pic_path\":\"http:aaaaa0-item_pic.jpg\",\"price\":\"110.90\",\"refund_status\":\"NO_REFUND\",\"seller_rate\":false,\"seller_type\":\"B\",\"sku_id\":\"42499235329\",\"sku_properties_name\":\"口味:奶油口味\",\"snapshot_url\":\"f:501699862933397_1\",\"status\":\"WAIT_SELLER_SEND_GOODS\",\"title\":\"【会员购巨献】老顽童 进口食品坚果炒货零食 年货大礼包限量秒杀\",\"total_fee\":\"119.70\"}]},\"pay_time\":\"2014-01-01 02:34:55\",\"payment\":\"119.70\",\"pic_path\":\"http:aaaaaa0-item_pic.jpg\",\"point_fee\":102,\"post_fee\":\"0.00\",\"price\":\"110.90\",\"promotion_details\":{\"promotion_detail\":[{\"discount_fee\":\"0.00\",\"id\":501699862933397,\"promotion_desc\":\"满就包邮:省0.00元\",\"promotion_id\":\"ManJiuDoWork426619940-225144224_1422626614\",\"promotion_name\":\"满就包邮\"},{\"discount_fee\":\"213.00\",\"id\":501699862933397,\"promotion_desc\":\"年底促销:省213.00元\",\"promotion_id\":\"MZDZ33760-219874632_1379626142\",\"promotion_name\":\"年底促销\"}]},\"real_point_fee\":0,\"received_payment\":\"0.00\",\"receiver_address\":\"超群街2723号海德世汽车拉索有限公司\",\"receiver_city\":\"长春市\",\"receiver_district\":\"高新技术产业开发区\",\"receiver_mobile\":\"13756295425\",\"receiver_name\":\"周宇\",\"receiver_phone\":\"\",\"receiver_state\":\"吉林省\",\"receiver_zip\":\"130000\",\"seller_alipay_no\":\"fudao.tm@furoad.com\",\"seller_cod_fee\":\"0.00\",\"seller_email\":\"fudao.tm@furoad.com\",\"seller_flag\":0,\"seller_mobile\":\"13760851174\",\"seller_nick\":\"老顽童旗舰店\",\"seller_phone\":\"\",\"seller_rate\":false,\"shipping_type\":\"express\",\"snapshot_url\":\"f:501699862933397_1\",\"status\":\"WAIT_SELLER_SEND_GOODS\",\"tid\":501699862933397,\"title\":\"老顽童旗舰店\",\"total_fee\":\"332.70\",\"trade_from\":\"WAP\",\"type\":\"fixed\"}}}";
        for (int i=0; i<1; i++){
            demoBusiness.sendMessage("{num:"+i+","+body+"}");
            try {
                System.out.println("thread name="+ Thread.currentThread().getName());
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("end time:"+DateUtils.getStandardDate(new Date()));
    }

}
