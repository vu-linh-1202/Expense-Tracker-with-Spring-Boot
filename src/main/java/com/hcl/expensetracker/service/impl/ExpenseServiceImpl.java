package com.hcl.expensetracker.service.impl;

import com.hcl.expensetracker.entity.Expense;
import com.hcl.expensetracker.repository.ExpenseRepository;
import com.hcl.expensetracker.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseServiceImpl implements ExpenseService {


    @Autowired
    private ExpenseRepository expenseRepo;

//    @Override
//    public List<Expense> getAllExpenses() {
//        return expenseRepo.findAll();
//    }

    @Override
    public Page<Expense> getAllExpenses(Pageable page) {
        return expenseRepo.findAll(page);
    }

    /**
     * Sử dụng Optional giúp tránh được việc xử lý giá trị null một cách rõ ràng, bằng cách sử dụng các phương thức của Optional như isPresent() để kiểm tra
     * xem đối tượng đã được tìm thấy hay chưa và get() để truy cập vào đối tượng khi nó có sẵn. Nếu đối tượng không có sẵn,
     * chúng ta có thể xử lý trường hợp này một cách ném ra ngoại lệ RuntimeException
     *
     * @param id
     * @return
     */
    @Override
    public Expense getExpenseById(Long id) {
        Optional<Expense> expense = expenseRepo.findById(id);
        if (expense.isPresent()) {
            return expense.get();
        }
        throw new RuntimeException("Expense is not found for the id " + id);
    }

    @Override
    public void deleteExpenseById(Long id) {
        expenseRepo.deleteById(id);
    }

    @Override
    public Expense saveExpenseDetails(Expense expense) {
        return expenseRepo.save(expense);
    }

    /**
     * @param id
     * @param expense
     * @return
     */
    @Override
    public Expense updateExpenseDetails(Long id, Expense expense) {
        Expense existingExpense = getExpenseById(id);
        existingExpense.setName(expense.getName() != null ? expense.getName() : existingExpense.getName());
        existingExpense.setDescription(expense.getDescription() != null ? expense.getDescription() : existingExpense.getDescription());
        existingExpense.setCategory(expense.getCategory() != null ? expense.getCategory() : existingExpense.getCategory());
        existingExpense.setAmount(expense.getAmount() != null ? expense.getAmount() : existingExpense.getAmount());
        existingExpense.setDate(expense.getDate() != null ? expense.getDate() : existingExpense.getDate());
        return expenseRepo.save(existingExpense);
    }
}
