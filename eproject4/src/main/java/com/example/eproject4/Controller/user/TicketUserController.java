package com.example.eproject4.Controller.user;

import com.example.eproject4.DTO.Request.CartItemRequest;
import com.example.eproject4.DTO.Response.MatchDTO;
import com.example.eproject4.Entity.Match;
import com.example.eproject4.Entity.Ticket;
import com.example.eproject4.Entity.User;
import com.example.eproject4.Entity.cart_order.Cart;
import com.example.eproject4.Entity.cart_order.Order;
import com.example.eproject4.Entity.cart_order.Ticket1;
import com.example.eproject4.Repository.MatchRepository;
import com.example.eproject4.Repository.TicketRepository;
import com.example.eproject4.Repository.cart_order.CartRepository;
import com.example.eproject4.Repository.cart_order.OrderRepository;
import com.example.eproject4.Service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("")
public class TicketUserController {
    @Autowired
    private VNPayService vnPayService;
    private final TicketService ticketService;
    private final TicketRepository ticketRepository;
    private final MatchService matchService;
    private final AreaService areaService;
    private final CartRepository cartRepository;
    private final CartService cartService;
    private final MatchRepository matchRepository;
    private final OrderRepository orderRepository;
    private String data;

    public TicketUserController(TicketService ticketService, TicketRepository ticketRepository, MatchService matchService, AreaService areaService, CartRepository cartRepository, CartService cartService, MatchRepository matchRepository, OrderRepository orderRepository) {
        this.ticketService = ticketService;
        this.ticketRepository = ticketRepository;
        this.matchService = matchService;
        this.areaService = areaService;
        this.cartRepository = cartRepository;
        this.cartService = cartService;
        this.matchRepository = matchRepository;
        this.orderRepository = orderRepository;
    }

//    @RequestMapping("/list_tickets")
//    public String tickets(Model model) {
//        model.addAttribute("overlay_title", "Tickets");
//        model.addAttribute("title", "Tickets");
//        model.addAttribute("description",
//                "Choose to buy tickets for upcoming matches.");
//
//        //Matches that have not yet taken place or are taking place 15 minutes before
//        List<Match> upcomingMatches = ticketService.getUpcomingMatches();
//        model.addAttribute("upcomingMatches", upcomingMatches);
//        //Matches have taken place or are taking place after 15 minutes
//        List<Match> pastMatches = ticketService.getPastMatches();
//        model.addAttribute("pastMatches", pastMatches);
//
//        return "customer_tickets";
//    }
@RequestMapping("/list_tickets")
public String tickets(Model model) {
    model.addAttribute("overlay_title", "Tickets");
    model.addAttribute("title", "Tickets");
    model.addAttribute("description", "Choose to buy tickets for upcoming matches.");
    return findPaginated(1, model);
}
@RequestMapping("/list_tickets/{pageNo}")
public String findPaginated(@PathVariable(value = "pageNo") int pageNo, Model model) {
    model.addAttribute("overlay_title", "Tickets");
    model.addAttribute("title", "Tickets");
    model.addAttribute("description", "Choose to buy tickets for upcoming matches.");
    List<Match> pastMatchess = ticketService.getPastMatches();
    List<Match> upcomingMatches = ticketService.getUpcomingMatches();
    // Lấy danh sách trận đấu đã diễn ra phân trang
    int pageSize = 6; // Số lượng trận đấu trên mỗi trang
    Page<Match> page = ticketService.findPaginatedticket(pageNo, pageSize,pastMatchess);
    List<Match> pastMatches = page.getContent();

    model.addAttribute("pastMatches", pastMatches);
    model.addAttribute("upcomingMatches", upcomingMatches);
    model.addAttribute("currentPage", pageNo);
    model.addAttribute("totalPages", page.getTotalPages());
    model.addAttribute("totalItems", page.getTotalElements());

    return "customer_tickets";
}

    @RequestMapping("/ticket/detail")
    public String ticketDetail(Model model, @RequestParam("matchid") int matchid, HttpSession session) {

        //lấy trận đấu
        MatchDTO match = matchService.getMatchById(Long.valueOf(matchid));
        if (match.getMatch_time().isBefore(LocalDateTime.now())) {
            return "redirect:/list_tickets";
        }
        model.addAttribute("match", match);

        //lấy danh sách ticket
        List<Ticket> ticketsbyMatch = ticketService.getAllTicketsByIdMath(matchid);
        model.addAttribute("ticketsbyMatch", ticketsbyMatch);
        List<Ticket> tickets = ticketRepository.findAll();
        model.addAttribute("tickets", tickets);

        // lấy cart
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        //int iduser = loggedInUser.getId().intValue();
        //List<Cart> carts = cartRepository.findAll();
        //List<Cart> carts = cartRepository.findByUserIdAndStatusFalse(loggedInUser.getId().intValue());
        List<Cart> carts = cartRepository.findByUserIdAndStatusFalse(15);
        model.addAttribute("carts", carts);

        // lấy toognr price
        float total = 0;
        for (Cart cart : carts) {
            total += cart.getQuantity() * cart.getTicket().getPrice();
        }
        model.addAttribute("total", total);


        CartItemRequest cart = new CartItemRequest(0L, 0);

        model.addAttribute("overlay_title", "Ticket Detail");
        model.addAttribute("title", "Ticket Detail");
        model.addAttribute("description", match.getHome_team().getName() + " vs " + match.getAway_team().getName());
        model.addAttribute("cartItemRequest", cart);
        return "customer_ticket_detail";
    }


    @PostMapping("/ticket/detail/ok")
    public String pay(@RequestBody String json, HttpServletRequest request) {
        try {
            // lấy tjhoong tin hóa đơn
            String a = json;
            String decodedString = URLDecoder.decode(a, "UTF-8");
            String cutString = decodedString.substring(11);
            ObjectMapper objectMapper = new ObjectMapper();
            data = cutString;
            List<Ticket1> tickets = objectMapper.readValue(cutString, new TypeReference<List<Ticket1>>(){});
            //Lấy tổng giá hóa đơn
            int total = 0;
            for (Ticket1 ticket1 : tickets) {
                total += ticket1.getPrice();
            }
            //boolean isPaySucccess = true;
            // xử lý thanh toán ở đây
            int orderTotal = total*24000*100;
            String orderInfo = "Thanh toan don hang: 123";
            String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
            String vnpayUrl = vnPayService.createOrder(orderTotal, orderInfo, baseUrl);
            return "redirect:" + vnpayUrl;

            } catch (Exception e) {
                return  e.getMessage();
            }

    }

    @GetMapping("/vnpay-payment")
    public String GetMapping(HttpServletRequest request, Model model, HttpSession session) {
        int paymentStatus = vnPayService.orderReturn(request);

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");

        model.addAttribute("orderId", orderInfo);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("paymentTime", paymentTime);
        model.addAttribute("transactionId", transactionId);

        if (paymentStatus == 1) {
            try {
                User loggedInUser = (User) session.getAttribute("loggedInUser");
                model.addAttribute("loggedInUser", loggedInUser);
                ObjectMapper objectMapper = new ObjectMapper();
                List<Ticket1> tickets = objectMapper.readValue(data, new TypeReference<List<Ticket1>>() {
                });
                // luu order
                Order order = new Order();
                order.setTotalPrice(Long.parseLong(totalPrice)/2400000);
                order.setUserId(loggedInUser.getId().intValue());
                //order.setUserId(15);
                order.setStatus(true);
                order.setCreatedAt(new Timestamp(System.currentTimeMillis()));
                Order ordersucss = orderRepository.save(order);

                // lưu cart vào db
                List<Cart> cartList = new ArrayList<>();
                for (Ticket1 ticket1 : tickets) {
                    Cart cart = new Cart();
                    cart.setPayment(true);
                    cart.setQuantity(ticket1.getQuantity());
                    cart.setUserId(loggedInUser.getId().intValue());
                    //cart.setUserId(15);
                    cart.setTicket((ticketRepository.findIdTicket(ticket1.getTicketId()).get(0)));
                    cart.setOrderid(ordersucss.getId());
                    cartList.add(cart);
                }
                cartService.saveCarts(cartList);
                for (Ticket1 ticket1 : tickets) {
                    Ticket ticketToUpdate = ticketRepository.findIdTicket(ticket1.getTicketId()).get(0);
                    if (ticketToUpdate != null) {
                        ticketToUpdate.setQuantity(ticketToUpdate.getQuantity()-ticket1.getQuantity());
                        ticketRepository.save(ticketToUpdate);
                    }
                }
                return "redirect:/success_order/" + ordersucss.getId();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            //return "customer_order_success";

        }
        return "error_notfound";
    }



}
