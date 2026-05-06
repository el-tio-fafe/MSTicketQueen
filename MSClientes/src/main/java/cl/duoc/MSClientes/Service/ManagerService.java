package cl.duoc.MSClientes.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.MSClientes.Repository.ManagerRepository;

@Service
public class ManagerService {

    @Autowired
    private ManagerRepository managerRepository;
}
