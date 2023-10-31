package com.example.eproject4.Controller.admin;

import com.example.eproject4.Entity.*;
import com.example.eproject4.Entity.cart_order.*;
import com.example.eproject4.Repository.UserRepository;
import com.example.eproject4.Repository.cart_order.CartRepository;
import com.example.eproject4.Repository.cart_order.OrderRepository;
import com.example.eproject4.Service.OrderService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
public class OrderController {
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final OrderService orderService;
    private final UserRepository userRepository;

    public OrderController(CartRepository cartRepository,OrderService orderService, OrderRepository orderRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.orderService = orderService;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }
    @GetMapping("/admin/order")
    public String getAllRoles(Model model) {
        List<Order> orders = orderRepository.findAll();
        List<User> users = userRepository.findAll();
        model.addAttribute("title", "Orders");
        model.addAttribute("orders", orders);
        model.addAttribute("users", users);
        return "admin_order";
    }

    @GetMapping("/admin/order/update/{id}")
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
        Order order = orderService.getOrderById(id);
        model.addAttribute("order", order); // Thay đổi tên attribute thành "order"
        return "admin_order_update";
    }

    @PostMapping("/admin/order/update/{id}")
    public String updateStatus(@PathVariable("id") int id,
                               @ModelAttribute("order") Order order) {
        // Trong trường hợp bạn muốn chỉ cập nhật trạng thái (status), bạn không cần lấy thông tin đơn hàng từ giao diện.
        // Sử dụng tham số id để cập nhật trạng thái
        orderService.updateOrderStatus(id, order.getStatus());
        return "redirect:/admin/order";
    }

    @GetMapping("/admin/listorder")
    public String listOrder(Model model, HttpSession session) {
        List<Order> listOrder = orderRepository.findAll();
        // sắp xep order
        Collections.sort(listOrder, new Comparator<Order>() {
            @Override
            public int compare(Order order1, Order order2) {
                // So sánh theo thuộc tính timestampField
                return order1.getCreatedAt().compareTo(order2.getCreatedAt());
            }
        });
        // Đảo ngược danh sách (từ lớn đến bé)
        Collections.reverse(listOrder);

        List<OrderDetailInfo1> listOrderRespond = new ArrayList<>();
        // lấy giỏ hàng
        for(Order order:listOrder){
            List<Cart> carts = cartRepository.findByOrder(order.getId());
            List<TicketDetailInfo> list = new ArrayList<>();
            for (Cart cart : carts) {
                // thông tin vé
                Ticket ticket = cart.getTicket();
                Match match = ticket.getMatch();
                Team teama = match.getHome_team();
                String name_a = teama.getName();
                String image_a = teama.getLogo_img();
                Team teamb = match.getAway_team_id();
                String name_b = teamb.getName();
                String image_b = teamb.getLogo_img();
                LocalDateTime time = match.getMatch_time();
                // Định dạng ngày
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String date = time.format(dateFormatter);
                // Định dạng giờ
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                String hour = time.format(timeFormatter);
                String stadium = match.getStadium_id().getName();
                Float price = ticket.getPrice();
                String area = ticket.getArea().getArea_name();
                // thêm thông tin vé
                TicketDetailInfo info = new TicketDetailInfo(name_a, image_a, name_b, image_b, date, hour, price, area, stadium, time);
                for (int i = 0; i < cart.getQuantity(); i++) {
                    list.add(info);
                }
            }
            String qrcode = CreateQrCode(order.getId());
            User user = userRepository.findById((long) order.getUserId()).orElse(null);
            listOrderRespond.add(new OrderDetailInfo1(order.getId(),user,order.getTotalPrice(),qrcode,list));
        }
        List<Order> orders = orderRepository.findAll();

        model.addAttribute("orders", orders);
        model.addAttribute("listOrderRespond", listOrderRespond);
        return "admin_list_order";
    }

    public String CreateQrCode(int idOrder) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = qrCodeWriter.encode("http://localhost:8080/print_ticket/" + Integer.toString(idOrder),
                    BarcodeFormat.QR_CODE, 200, 200);

            // Chuyển BitMatrix thành hình ảnh BufferedImage
            BufferedImage image = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < 200; x++) {
                for (int y = 0; y < 200; y++) {
                    image.setRGB(x, y, bitMatrix.get(x, y) ? 0x000000 : 0xFFFFFF);
                }
            }
            // Lưu hình ảnh vào model để hiển thị trên trang Thymeleaf
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            byte[] imageBytes = baos.toByteArray();
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
            String imageSrc = "data:image/png;base64," + base64Image;
            return imageSrc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
