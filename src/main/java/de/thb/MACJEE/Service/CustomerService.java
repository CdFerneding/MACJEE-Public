package de.thb.MACJEE.Service;

import de.thb.MACJEE.Controller.form.CustomerSettingsForm;
import de.thb.MACJEE.Entitys.Enumerations.Characteristics;
import de.thb.MACJEE.Entitys.Job;
import de.thb.MACJEE.Entitys.Skill;
import de.thb.MACJEE.Repository.CustomerRepository;
import de.thb.MACJEE.Repository.JobRepository;
import de.thb.MACJEE.Repository.SkillRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import de.thb.MACJEE.Entitys.Customer;

@Service
@Data
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private SkillRepository skillRepository;
    /*@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("username not found!"));
        return new User(customer.getUsername(), customer.getPassword(), mapRolesToAuthorities(customer.getRoles()));
    }

    private Collection<GrantedAuthority> mapRolesToAuthorities(List<Role> roles) {
        return roles.stream().map((role) -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }*/

    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findCustomerById(id);
    }

    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }
    public List<Job> getApplicationsOfCustomer(Long customerId) {
        return customerRepository.findApplicationsOfCustomer(customerId);
    }

    public Optional<Customer> getCustomerByUserName(String userName) throws UsernameNotFoundException {
        return customerRepository.findCustomerByUsernameWithApplications(userName);
    }

    public Optional<Customer> getCustomerByUsernameWithSkills(String userName) throws UsernameNotFoundException {
        return customerRepository.findCustomerByUsernameWithSkills(userName);
    }

    public void setCustomerWorkingAt(Customer customer, Job job) {
        customer.setCurrentJob(job);
        job.setWorking(customer);
        customerRepository.save(customer);
        jobRepository.save(job);
    }

    public boolean CustomerHasJob(Customer customer) {
        return customerRepository.customerHasCurrentJob(customer);
    }

    public void removeApplication(Job job, Customer customer) {
        List<Customer> applicants = job.getApplicants();
        // for-each instead of "customer.applications.remove(job), because not every attribute is loaded
        // therefore the objects are not properly compared
        // edit: for-each threw "ConcurrentModificationException"
        Iterator<Customer> applicantIterator = applicants.iterator();
        while (applicantIterator.hasNext()) {
            Customer applicant = applicantIterator.next();
            if (customer.getId().equals(applicant.getId())) {
                applicantIterator.remove();
            }
        }

        List<Job> applications = customer.getApplications();
        Iterator<Job> applicationIterator = applications.iterator();
        while (applicationIterator.hasNext()) {
            Job application = applicationIterator.next();
            if (job.getId().equals(application.getId())) {
                applicationIterator.remove();
            }
        }

        customerRepository.save(customer);
        jobRepository.save(job);
    }

    public Optional<Customer> getCustomerWithApplications(String username) {
        return customerRepository.findCustomerByUsernameWithApplications(username);
    }

    public void Settings(Customer customer, String changes, CustomerSettingsForm customerSettingsForm){
        switch (changes) {
            case "doB" -> {
                String dateOfBirthString = customerSettingsForm.getDoB();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date dateOfBirth;
                try {
                    dateOfBirth = dateFormat.parse(dateOfBirthString);
                    customer.setDoB(dateOfBirth);
                    customerRepository.save(customer);
                } catch (ParseException e) {
                    //model.addAttribute("changes", "doB");
                    //return "user/customerSettings";
                }
            }
            case "mail" -> {
                customer.setMail(customerSettingsForm.getMail());
                customerRepository.save(customer);
            }
            case "name" -> {
                customer.setFirstName(customerSettingsForm.getFirstName());
                customer.setLastName(customerSettingsForm.getLastName());
                customerRepository.save(customer);
            }
            case "newSkill" -> {
                List<Customer> customers = new ArrayList<>(Collections.singletonList(customer));

                List<Skill> skills = new ArrayList<Skill>();
                int i = 0;
                for(Characteristics skillName : Characteristics.values()) {
                    Skill skill = new Skill();
                    skill.setName(skillName.toString());
                    skill.setLevel((long) customerSettingsForm.getValue().get(i));
                    skill.setCustomers(customers);
                    skillRepository.save(skill);
                    skills.add(skill);
                    i++;
                }
                customer.setSkills(skills);
                customerRepository.save(customer);
            }
        }
    }
}
