package code.uz.service;

import code.uz.dto.Terminal;
import code.uz.enums.Status;
import code.uz.repository.TerminalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TerminalService {

    @Autowired
    private TerminalRepository terminalRepository;

    public void updateTerminalByCode(Terminal terminal) {
        Terminal exist = terminalRepository.getTerminalByCode(terminal.getCode());
        if (exist == null) {
            System.out.println("Terminal not found");
            return;
        }

        terminalRepository.updateTerminal(terminal);
        System.out.println("terminal address updated");
    }

    public void changeTerminalStatus(String code) {
        Terminal terminal = terminalRepository.getTerminalByCode(code);
        if (terminal == null) {
            System.out.println("Terminal code not exists");
            return;
        }

        if (terminal.getStatusTerminal().equals(Status.ACTIVE)) {
            terminalRepository.changeTerminalStatus(code, Status.BLOCKED);
            System.out.println("Status changed");

        } else if (terminal.getStatusTerminal().equals(Status.BLOCKED)) {
            terminalRepository.changeTerminalStatus(code, Status.ACTIVE);
            System.out.println("Status changed");
        }

    }

    public void delete(String code) {
        Terminal terminal = terminalRepository.getTerminalByCode(code);
        if (terminal == null) {
            System.out.println("Terminal not found");
            return;
        }
        terminalRepository.delete(terminal);
        System.out.println("Terminal deleted ");
    }


    public void adminCreateTerminal(Terminal terminal) {

        Terminal exist = terminalRepository.getTerminalByCode(terminal.getCode());
        if (exist != null) {
            System.out.println("Terminal code exists");
            return;
        }

        terminal.setCreatedDate(LocalDateTime.now());
        terminal.setStatusTerminal(Status.ACTIVE);
        terminal.setVisible(true);

        if (terminalRepository.adminCreateTerminal(terminal, true) > 0) {
            System.out.println("terminal successfully created");
        }

    }

    public void getTerminalList() {

        List<Terminal> terminalList = terminalRepository.getAllTerminal();
        if (terminalList.isEmpty()) {
            System.out.println("\nTerminal list is empty!!!\n");
            return;
        }
        System.out.println("\t\t\t\t\t\t\t\t\t\t ********** Terminal list **********\n");
        for (Terminal terminal : terminalList) {
            System.out.println(terminal);
        }
        System.out.println();

    }

    public void setTerminalRepository(TerminalRepository terminalRepository) {
        this.terminalRepository = terminalRepository;
    }
}
