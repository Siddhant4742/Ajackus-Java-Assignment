
import pytest
import requests

# Configuration
BASE_URL = "http://65.2.37.186:5000"

@pytest.fixture
def sample_book():
    """Fixture to provide a consistent test book"""
    return {
        "id": 100,
        "title": "Test Book",
        "author": "Test Author", 
        "genre": "Test Genre",
        "availabilityStatus": "Available"
    }

class TestLibraryServer:
    def test_add_book(self, sample_book):
        """Test adding a new book to the library"""
        response = requests.post(f"{BASE_URL}/books", json=sample_book)
        assert response.status_code == 201, f"Failed to add book. Response: {response.text}"
        
        # Verify the returned book matches the input
        returned_book = response.json()
        assert returned_book['title'] == sample_book['title']

    def test_get_all_books(self):
        """Test retrieving all books"""
        response = requests.get(f"{BASE_URL}/books")
        assert response.status_code == 200
        
        books = response.json()
        assert len(books) > 0, "No books found in the library"

    def test_search_book_by_title(self, sample_book):
        """Test searching for books by title"""
        response = requests.get(f"{BASE_URL}/books/search", 
                                params={"title": sample_book['title']})
        assert response.status_code == 200
        
        books = response.json()
        assert any(book['title'] == sample_book['title'] for book in books), \
            "Searched book not found"

    def test_get_book_by_id(self, sample_book):
        """Test retrieving a specific book by ID"""
        response = requests.get(f"{BASE_URL}/books/{sample_book['id']}")
        assert response.status_code == 200
        
        book = response.json()
        assert book['id'] == sample_book['id']
        assert book['title'] == sample_book['title']

    def test_update_book(self, sample_book):
        """Test updating an existing book"""
        updated_book = sample_book.copy()
        updated_book['title'] = "Updated Test Book"
        
        response = requests.put(f"{BASE_URL}/books/{sample_book['id']}", 
                                json=updated_book)
        assert response.status_code == 200
        
        # Verify the book was actually updated
        verify_response = requests.get(f"{BASE_URL}/books/{sample_book['id']}")
        updated_book_data = verify_response.json()
        assert updated_book_data['title'] == "Updated Test Book"

    def test_delete_book(self, sample_book):
        """Test deleting a book"""
        response = requests.delete(f"{BASE_URL}/books/{sample_book['id']}")
        assert response.status_code == 200
        
        # Verify the book is actually deleted
        verify_response = requests.get(f"{BASE_URL}/books/{sample_book['id']}")
        assert verify_response.status_code == 404

# Error handling and edge case tests
class TestErrorScenarios:
    def test_add_book_with_invalid_data(self):
        """Test adding a book with incomplete data"""
        invalid_book = {
            "title": "Incomplete Book"
        }
        response = requests.post(f"{BASE_URL}/books", json=invalid_book)
        assert response.status_code in [400, 500]

    def test_get_nonexistent_book(self):
        """Test retrieving a book with a non-existent ID"""
        response = requests.get(f"{BASE_URL}/books/99999")
        assert response.status_code == 404

# Run configuration
if __name__ == "__main__":
    pytest.main([
        "-v",  # verbose output
        "--cov=.",  # generate coverage report
        "--cov-report=html",  # HTML coverage report
        "--cov-report=term",  # terminal summary
        "--cov-fail-under=80"  # fail if coverage is below 80%
    ])