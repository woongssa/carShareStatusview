package carshare;

import carshare.config.kafka.KafkaProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerpageViewHandler {


    @Autowired
    private CustomerpageRepository customerpageRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenOrdered_then_CREATE_1 (@Payload Ordered ordered) {
        try {
            if (ordered.isMe()) {
                // view 객체 생성
                Customerpage customerpage = new Customerpage();
                // view 객체에 이벤트의 Value 를 set 함
                customerpage.setOrderId(ordered.getId());
                customerpage.setProductId(ordered.getProductId());
                customerpage.setQty(ordered.getQty());
                // view 레파지 토리에 save
                customerpageRepository.save(customerpage);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whenShipped_then_UPDATE_1(@Payload Shipped shipped) {
        try {
            if (shipped.isMe()) {
                // view 객체 조회
                List<Customerpage> customerpageList = customerpageRepository.findByOrderId(shipped.getOrderId());
                for(Customerpage customerpage : customerpageList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    customerpage.setDeliveryId(shipped.getId());
                    customerpage.setStatus(shipped.getStatus());
                    // view 레파지 토리에 save
                    customerpageRepository.save(customerpage);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenDeliveryCanceled_then_UPDATE_2(@Payload DeliveryCanceled deliveryCanceled) {
        try {
            if (deliveryCanceled.isMe()) {
                // view 객체 조회
                List<Customerpage> customerpageList = customerpageRepository.findByOrderId(deliveryCanceled.getOrderId());
                for(Customerpage customerpage : customerpageList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    customerpage.setStatus(deliveryCanceled.getStatus());
                    // view 레파지 토리에 save
                    customerpageRepository.save(customerpage);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenPaid_then_UPDATE_3(@Payload Paid paid) {
        try {
            if (paid.isMe()) {
                // view 객체 조회
                List<Customerpage> customerpageList = customerpageRepository.findByOrderId(paid.getOrderId());
                for(Customerpage customerpage : customerpageList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    customerpage.setPaymentId(paid.getId());
                    // view 레파지 토리에 save
                    customerpageRepository.save(customerpage);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenOrderCancelled_then_DELETE_1(@Payload OrderCancelled orderCancelled) {
        try {
            if (orderCancelled.isMe()) {
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}