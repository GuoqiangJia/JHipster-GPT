package com.genhot.shopper.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.genhot.shopper.IntegrationTest;
import com.genhot.shopper.domain.Address;
import com.genhot.shopper.repository.AddressRepository;
import com.genhot.shopper.repository.EntityManager;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for the {@link AddressResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class AddressResourceIT {

    private static final String DEFAULT_ADDRESS_1 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_1 = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    private static final String DEFAULT_ZIPCODE = "AAAAAAAAAA";
    private static final String UPDATED_ZIPCODE = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/addresses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private Address address;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Address createEntity(EntityManager em) {
        Address address = new Address()
            .address1(DEFAULT_ADDRESS_1)
            .city(DEFAULT_CITY)
            .state(DEFAULT_STATE)
            .zipcode(DEFAULT_ZIPCODE)
            .country(DEFAULT_COUNTRY);
        return address;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Address createUpdatedEntity(EntityManager em) {
        Address address = new Address()
            .address1(UPDATED_ADDRESS_1)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .zipcode(UPDATED_ZIPCODE)
            .country(UPDATED_COUNTRY);
        return address;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(Address.class).block();
        } catch (Exception e) {
            // It can fail, if other entities are still referring this - it will be removed later.
        }
    }

    @AfterEach
    public void cleanup() {
        deleteEntities(em);
    }

    @BeforeEach
    public void initTest() {
        deleteEntities(em);
        address = createEntity(em);
    }

    @Test
    void createAddress() throws Exception {
        int databaseSizeBeforeCreate = addressRepository.findAll().collectList().block().size();
        // Create the Address
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(address))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll().collectList().block();
        assertThat(addressList).hasSize(databaseSizeBeforeCreate + 1);
        Address testAddress = addressList.get(addressList.size() - 1);
        assertThat(testAddress.getAddress1()).isEqualTo(DEFAULT_ADDRESS_1);
        assertThat(testAddress.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testAddress.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testAddress.getZipcode()).isEqualTo(DEFAULT_ZIPCODE);
        assertThat(testAddress.getCountry()).isEqualTo(DEFAULT_COUNTRY);
    }

    @Test
    void createAddressWithExistingId() throws Exception {
        // Create the Address with an existing ID
        address.setId(1L);

        int databaseSizeBeforeCreate = addressRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(address))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll().collectList().block();
        assertThat(addressList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllAddresses() {
        // Initialize the database
        addressRepository.save(address).block();

        // Get all the addressList
        webTestClient
            .get()
            .uri(ENTITY_API_URL + "?sort=id,desc")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.[*].id")
            .value(hasItem(address.getId().intValue()))
            .jsonPath("$.[*].address1")
            .value(hasItem(DEFAULT_ADDRESS_1))
            .jsonPath("$.[*].city")
            .value(hasItem(DEFAULT_CITY))
            .jsonPath("$.[*].state")
            .value(hasItem(DEFAULT_STATE))
            .jsonPath("$.[*].zipcode")
            .value(hasItem(DEFAULT_ZIPCODE))
            .jsonPath("$.[*].country")
            .value(hasItem(DEFAULT_COUNTRY));
    }

    @Test
    void getAddress() {
        // Initialize the database
        addressRepository.save(address).block();

        // Get the address
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, address.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(address.getId().intValue()))
            .jsonPath("$.address1")
            .value(is(DEFAULT_ADDRESS_1))
            .jsonPath("$.city")
            .value(is(DEFAULT_CITY))
            .jsonPath("$.state")
            .value(is(DEFAULT_STATE))
            .jsonPath("$.zipcode")
            .value(is(DEFAULT_ZIPCODE))
            .jsonPath("$.country")
            .value(is(DEFAULT_COUNTRY));
    }

    @Test
    void getNonExistingAddress() {
        // Get the address
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_PROBLEM_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingAddress() throws Exception {
        // Initialize the database
        addressRepository.save(address).block();

        int databaseSizeBeforeUpdate = addressRepository.findAll().collectList().block().size();

        // Update the address
        Address updatedAddress = addressRepository.findById(address.getId()).block();
        updatedAddress
            .address1(UPDATED_ADDRESS_1)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .zipcode(UPDATED_ZIPCODE)
            .country(UPDATED_COUNTRY);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedAddress.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedAddress))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll().collectList().block();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);
        Address testAddress = addressList.get(addressList.size() - 1);
        assertThat(testAddress.getAddress1()).isEqualTo(UPDATED_ADDRESS_1);
        assertThat(testAddress.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testAddress.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testAddress.getZipcode()).isEqualTo(UPDATED_ZIPCODE);
        assertThat(testAddress.getCountry()).isEqualTo(UPDATED_COUNTRY);
    }

    @Test
    void putNonExistingAddress() throws Exception {
        int databaseSizeBeforeUpdate = addressRepository.findAll().collectList().block().size();
        address.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, address.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(address))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll().collectList().block();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchAddress() throws Exception {
        int databaseSizeBeforeUpdate = addressRepository.findAll().collectList().block().size();
        address.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(address))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll().collectList().block();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamAddress() throws Exception {
        int databaseSizeBeforeUpdate = addressRepository.findAll().collectList().block().size();
        address.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(address))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll().collectList().block();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateAddressWithPatch() throws Exception {
        // Initialize the database
        addressRepository.save(address).block();

        int databaseSizeBeforeUpdate = addressRepository.findAll().collectList().block().size();

        // Update the address using partial update
        Address partialUpdatedAddress = new Address();
        partialUpdatedAddress.setId(address.getId());

        partialUpdatedAddress.state(UPDATED_STATE).country(UPDATED_COUNTRY);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedAddress.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedAddress))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll().collectList().block();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);
        Address testAddress = addressList.get(addressList.size() - 1);
        assertThat(testAddress.getAddress1()).isEqualTo(DEFAULT_ADDRESS_1);
        assertThat(testAddress.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testAddress.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testAddress.getZipcode()).isEqualTo(DEFAULT_ZIPCODE);
        assertThat(testAddress.getCountry()).isEqualTo(UPDATED_COUNTRY);
    }

    @Test
    void fullUpdateAddressWithPatch() throws Exception {
        // Initialize the database
        addressRepository.save(address).block();

        int databaseSizeBeforeUpdate = addressRepository.findAll().collectList().block().size();

        // Update the address using partial update
        Address partialUpdatedAddress = new Address();
        partialUpdatedAddress.setId(address.getId());

        partialUpdatedAddress
            .address1(UPDATED_ADDRESS_1)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .zipcode(UPDATED_ZIPCODE)
            .country(UPDATED_COUNTRY);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedAddress.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedAddress))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll().collectList().block();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);
        Address testAddress = addressList.get(addressList.size() - 1);
        assertThat(testAddress.getAddress1()).isEqualTo(UPDATED_ADDRESS_1);
        assertThat(testAddress.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testAddress.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testAddress.getZipcode()).isEqualTo(UPDATED_ZIPCODE);
        assertThat(testAddress.getCountry()).isEqualTo(UPDATED_COUNTRY);
    }

    @Test
    void patchNonExistingAddress() throws Exception {
        int databaseSizeBeforeUpdate = addressRepository.findAll().collectList().block().size();
        address.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, address.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(address))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll().collectList().block();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchAddress() throws Exception {
        int databaseSizeBeforeUpdate = addressRepository.findAll().collectList().block().size();
        address.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(address))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll().collectList().block();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamAddress() throws Exception {
        int databaseSizeBeforeUpdate = addressRepository.findAll().collectList().block().size();
        address.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(address))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll().collectList().block();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteAddress() {
        // Initialize the database
        addressRepository.save(address).block();

        int databaseSizeBeforeDelete = addressRepository.findAll().collectList().block().size();

        // Delete the address
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, address.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<Address> addressList = addressRepository.findAll().collectList().block();
        assertThat(addressList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
