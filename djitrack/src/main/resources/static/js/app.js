// Dans vos fichiers JS
async function apiCall(url, options = {}) {
    const token = localStorage.getItem('djitrack_token');
    const headers = {
        'Content-Type': 'application/json',
        ...(token ? { 'Authorization': `Bearer ${token}` } : {})
    };

    const response = await fetch(url, {
        ...options,
        headers: { ...headers, ...options.headers }
    });

    if (response.status === 401) {
        window.location.href = '/login';
        return;
    }

    return response;
}